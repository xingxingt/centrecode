package cn.spark.core

import scala.collection.mutable

object ListTest {

  def main(args: Array[String]): Unit = {
    var list: mutable.MutableList[Int] = mutable.MutableList()
    list=list :+ 1
    list=list :+ 2
    list=list :+ 3
    list.foreach(println)

    list(0)=4
    list.foreach(println)


  }

}
