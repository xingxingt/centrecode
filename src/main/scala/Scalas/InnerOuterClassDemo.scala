package Scalas

class InnerOuterClassDemo(val name: String) { //外部类


  outer => //这句相当于给this起了一个别名为outer

  class Inner(val name: String) {
    //定义内部类的别名:outer

    def foo(b: Inner): Unit = {
      println(s"Outer name:${outer.name}   Inner name:${b.name}")
    }
  }

}


object InnerOuterClassTest {
  def main(args: Array[String]): Unit = {

    val out1 = new InnerOuterClassDemo("spark")
    val out2 = new InnerOuterClassDemo("hadoop")


    val inner1 = new out1.Inner("scala")
    val inner2 = new out2.Inner("java")

    inner1.foo(inner1)
    inner2.foo(inner2)


  }
}
