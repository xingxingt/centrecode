package com.webank.qml.test

class swingType {
  def wantLearned(swType: String) = {
    println("sw type:" + swType)
  }
}

object swing {
  implicit def learnType(s: ImplicitDemo) = new swingType()
}


class ImplicitDemo {

  object ImplicitDemo {
    def main(args: Array[String]): Unit = {
      val demo = new ImplicitDemo()
      import com.webank.qml.test.swing._
      demo.wantLearned("ziyou")
    }
  }

}
