package Scalas

/**
  *【scala对象的getter和setter方法】
  *Scala默认对于GetterSetterDemo类的_privateAge属性，自动生成的getter和setter方法名分别是_privateAge和_privateAge_=,并且不容许重写此方法
  *所以在定义私有属性的getter和setter方法时要避开重复的方法名
  */
class GetterSetterDemo {
  private var _privateAge=0 //私有属性
  var name="scala"

  def privateAge = _privateAge //getter
  def privateAge_=(aprivateAge: Int) {_privateAge = aprivateAge} //setter

  def haveatry(): Unit = {
    println("hava a try")
  }

}
//todo 伴生对象
object GetterSetterDemo{
  def apply(): GetterSetterDemo ={
    println("===============object apply")
    new GetterSetterDemo()
  }
}

object GtTest{
  def main(args: Array[String]): Unit = {

    val gt=GetterSetterDemo()
    gt.haveatry()
    gt.privateAge_=(12)
   println(gt.privateAge)

  }
}
