package cn.spark.streaming

import cn.spark.util.SparkUtils
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}


/**
  * 【ForeachRdd api demo】
  * 可以在Dstream的基础上进行RDD的操作
  */
object ForeachRddDemo {

  def main(args: Array[String]): Unit = {

    val ssc = SparkUtils.getLocalStreamingContext("WindowFuns", 8)
    ssc.checkpoint("/Users/axing/opt/others/logs/")

    val lines = ssc.socketTextStream("localhost", 9999)
    // Split each line into words
    val words = lines.flatMap(_.split(" "))

    // Count each word in each batch
    val pairs = words.map(word => (word, 1))

    val wordCounts = pairs.reduceByKey(_ + _)

    //可以在此保存计算的结果数据向mysql，hbase,hive中写数据
    wordCounts.foreachRDD(rdd => {
      //操作每个partition
      rdd.foreachPartition(partitionRecoders => {
        //1，创建资源链接
        //操作每条数据
        partitionRecoders.foreach(recoder => {
          //2，创建sql进行写数据操作
        })
      })
      //3，关闭或者归还资源链接

    })


  }

}
