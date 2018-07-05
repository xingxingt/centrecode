package cn.spark.core

object FanXing {


  def main(args: Array[String]): Unit = {

    def position[A](xs: List[A], value: A): Int = {
      xs.indexOf(value)
    }

    println(position(List(1,2,3), 1))
    println(position(List("one", "two", "three"), "two")) // 1


  }

}
