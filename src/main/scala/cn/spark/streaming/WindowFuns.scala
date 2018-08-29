package cn.spark.streaming

import java.text.SimpleDateFormat
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 【窗口函数】
  */
object WindowFuns {
  private val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[2]").setAppName("WindowFuns")
    val spark = SparkSession.builder().appName("windowFun").config(conf).getOrCreate()
    val sc = spark.sparkContext
    sc.setLogLevel("WARN")
    val ssc = new StreamingContext(sc, Seconds(5))
    ssc.checkpoint("/Users/axing/opt/others/logs/")

    val lines = ssc.socketTextStream("localhost", 9999)
    // Split each line into words
    val words = lines.flatMap(_.split(" "))

    // Count each word in each batch
    val pairs = words.map(word => (word, 1))

    /** 每20秒钟计算一次 窗口大小60秒  不会复用前面的结果，每次都会计算前面60秒钟的结果
      * val result = pairs.reduceByKeyAndWindow((v1: Int, v2: Int) => {
      * v1 + v2
      * }, Seconds(60), Seconds(20))
      */

    /**
      * 每20秒钟计算一次 窗口大小60秒  复用前面的结果，每次都会复用前面40秒钟的结果 必须进行checkPoint
      * (a:Int, b:Int) => a + b  :对过去的60秒钟加上新的20秒钟
      * (a:Int, b:Int) => a - b  :对新的结果再减去过去20秒钟
      * todo:是因为每20秒钟计算一次会复用过去40秒钟的数据
      */
    val result = pairs.reduceByKeyAndWindow((a: Int, b: Int) => a + b, (a: Int, b: Int) => a + b, Seconds(60), Seconds(20))
    result.print()

    ssc.start()
    ssc.awaitTermination()
  }
}