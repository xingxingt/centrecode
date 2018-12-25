package cn.spark.core

import cn.spark.util.SparkUtils

/**
  * 根据权重将一个RDD拆分出多个RDD
  */
object RandomSplitDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkUtils.getLocalSparkSession("RandomSplitDemo")
    val sc = spark.sparkContext
    val rdd = sc.makeRDD(1 to 200, 10)
    val splitRdd = rdd.randomSplit(Array[Double](1.0, 3.0, 5.0, 1.0))
    for (itemRdd <- splitRdd) {
      println("---------------------------------")
      itemRdd.collect().foreach(println)
      println("---------------------------------")
    }

    spark.stop()
  }

}
