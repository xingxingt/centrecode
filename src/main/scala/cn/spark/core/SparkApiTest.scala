package cn.spark.core

import org.apache.spark.{SparkConf, SparkContext}

object SparkApiTest {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("sparkApi").setMaster("local[2]")
    val sc = new SparkContext(conf)

    filterMethod(sc)


  }

  def filterMethod(sc: SparkContext): Unit = {
    val data = Array(1, 2, 3, 4, 5)
    val distData = sc.parallelize(data)
    val firesult = distData.filter(_ > 2)

    firesult.collect().foreach(println)
  }

}
