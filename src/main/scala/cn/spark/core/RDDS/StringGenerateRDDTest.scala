package cn.spark.core.RDDS

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 自定义StringGenerateRDD测试
  */
object StringGenerateRDDTest {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("StringGenerateRDDTest").setMaster("local[2]")
    val sc = new SparkContext(conf)

    val  rdd = new StringGenerateRDD(sc,"sun biao biao\n sun biao biao\nsun biao biao\n sun biao biao\nsun biao biao\n sun biao biao\nsun biao biao\n sun biao biao\nsun biao biao\n sun biao biao\n", 3)

    rdd.flatMap(l => l.split(" ")).map(x => (x, 1)).reduceByKey(_ + _).collect().foreach(println)

  }

}
