package cn.spark.core

import cn.spark.util.SparkUtils

import scala.collection.mutable

/**
  * 分組取topN
  */
object GroupTopN {

  def main(args: Array[String]): Unit = {

    val sc = SparkUtils.getSparkContext("GroupTopN")
    val data = sc.textFile("F:\\dataTest\\groupTopN.txt")
    val lines = data.map { line => (line.split(" ")(0), line.split(" ")(1).toInt) }
    val groups = lines.groupByKey()
    val groupsSort = groups.map(tu => {
      val key = tu._1
      val values = tu._2
      val sortValues = values.toList.sortWith(_ > _).take(5)
      (key, sortValues)
    })

    groupsSort.sortBy(tu => tu._1, false, 1).collect().foreach(value => {
      print(value._1)
      value._2.foreach(v => print("\t" + v))
      println()
    })

    sc.stop()
  }
}
