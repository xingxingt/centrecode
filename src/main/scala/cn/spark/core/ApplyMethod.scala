package cn.spark.core

class  ApplyMethod{

  def prin(unit:String) = println("class method")

}



object ApplyMethod {

  def apply() :ApplyMethod ={
    print("this is a apply method")
    new ApplyMethod()
  }

}



