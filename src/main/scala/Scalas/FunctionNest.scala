package Scalas


import scala.collection.mutable.ArrayBuffer

object FunctionNest {

  def main(args: Array[String]): Unit = {

    val tasks =new ArrayBuffer[String](10)
    contian(tasks)

    println(tasks(0))
  }

  def contian(tasks:ArrayBuffer[String]): Boolean ={
    tasks+="11111";
    true
  }

}
