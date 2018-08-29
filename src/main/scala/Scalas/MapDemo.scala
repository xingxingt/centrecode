package Scalas

/**
  * 测试可变不可变的Map
  */
object MapDemo {

  def main(args: Array[String]): Unit = {
    //todo 不可变map
    val map1 = scala.collection.immutable.Map("book" -> 10, "java" -> 20, "scala" -> 30)
    for ((k, v) <- map1) yield (k, v * 0.9)

    //    map1 += ("spark" -> 20)   //如果想要执行添加和移除操作 则map声明的时候应为var
    map1.foreach(println)

    println("================================")

    //todo 可变map
    val map2 = scala.collection.mutable.Map("book" -> 10, "java" -> 20, "scala" -> 30)
    map2 += ("spark" -> 20)
    map2 -= "book"
    println(map2.getOrElse("book",0))
    map2.foreach(println)


  }

}
