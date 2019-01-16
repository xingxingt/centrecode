package com.webank.qml.test

import org.junit.Test

class PartialFunctionDemo {

  @Test
  def test(): Unit = {
    //笨拙方法定义偏函数([Any, Int] => any:输入参数 Int：返回类型)
    val inc = new PartialFunction[Any, Int] {
      //apply方法用来描述对已接受的值如何处理
      override def isDefinedAt(x: Any): Boolean = {
        if (x.isInstanceOf[Int]) true else false
      }

      //isDefinedAt用来告知调用方这个偏函数接受参数的范围，可以是类型也可以是值
      override def apply(v1: Any): Int = {
        v1.asInstanceOf[Int] + 1
      }
    }

    //使用case简洁定义偏函数
    def inc_easy: PartialFunction[Any, Int] = {
      case i: Int => i + 1
    }

    //collect,它声明接受的是一个偏函数
    val list = List(1, 2, 3, "four").collect(inc_easy)
    list.foreach(print)


  }


}
