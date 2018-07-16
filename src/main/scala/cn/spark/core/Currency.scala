package cn.spark.core

class Currency(val value: Double, val unit: String) {
  def prints(ont: String, tow: String) = print(ont + tow)
}


object Currency {
  def apply(value: Double, unit: String): Currency = {
    println("apply method")
    new Currency(value, unit)
  }
  def printobj = println("obj method excute")
}


object Test2 {
  def main(args: Array[String]): Unit = {
    val currency = Currency(30.2, "EUR")
    currency.prints("1", "2")
    Currency.printobj
  }
}
