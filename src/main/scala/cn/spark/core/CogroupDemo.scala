package cn.spark.core

import cn.spark.util.SparkUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  * 【将多个RDD中同一个Key对应的Value组合到一起】
  */
object CogroupDemo {

  def main(args: Array[String]): Unit = {
    val sc = SparkUtils.getLocalSparkContext("CogroupDemo")
    val data1 = sc.parallelize(List((1, "www"), (2, "bbs")))
    val data2 = sc.parallelize(List((1, "iteblog"), (2, "iteblog"), (3, "very")))
    val data3 = sc.parallelize(List((1, "com"), (2, "com"), (3, "good")))
    val result = data1.cogroup(data2, data3)
    result.collect.foreach(println)
    //todo  输出结果
    //(2,(CompactBuffer(bbs),CompactBuffer(iteblog),CompactBuffer(com)))
    //(1,(CompactBuffer(www),CompactBuffer(iteblog),CompactBuffer(com)))
    //(3,(CompactBuffer(),CompactBuffer(very),CompactBuffer(good)))

    //取出所有的key value
    result.foreach(item => {
      println("key------" + item._1)
      println("value----" + item._2._1.mkString(""))
    })


  }

}
