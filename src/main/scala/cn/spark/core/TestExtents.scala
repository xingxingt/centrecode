
class TestExtents {

}

class Point(val xc: Int, val yc: Int) {
  var x: Int = xc
  var y: Int = yc
  def move(dx: Int, dy: Int) {
    x = x + dx
    y = y + dy
    println ("x 的坐标点 : " + x);
    println ("y 的坐标点 : " + y);
  }
}


class Location(override val xc: Int, override val yc: Int,
               val zc :Int) extends Point(xc, yc){
  var z: Int = zc

  def move(dx: Int, dy: Int, dz: Int) {

    println(xc+"----"+yc)

    x = x + dx
    y = y + dy
    z = z + dz
    println ("x 的坐标点 : " + x);
    println ("y 的坐标点 : " + y);
    println ("z 的坐标点 : " + z);
  }
}


object Test {
  def main(args: Array[String]) {

//    var point=new Point(10,12)
//    point.move(10,12)


    val loc = new Location(10, 20, 15);
    // 移到一个新的位置
    loc.move(10, 10);
  }
}


//--------------------------------------------------------------

class Person1 {
  var name = ""
  override def toString = getClass.getName + "[name=" + name + "]"
  def person_say(): Unit ={
    print("---------hello")
  }
}

class Employee extends Person1 {
  var salary = 0.0
  override def toString = super.toString + "[salary=" + salary + "]"
  override def person_say(): Unit ={
    print("---------hello")
  }

}

object Test1 extends App {
  val fred = new Employee

  fred.name = "Fred"
  fred.salary = 50000
  println(fred)
  fred.person_say()


}


//---------------------------------------------------------------------------

