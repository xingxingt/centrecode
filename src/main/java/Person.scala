import org.apache.spark.sql.SparkSession

class Person{

  implicit class Person(val name:String){
    def sayHello = s"Hello, I'm $name."
  }

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("")
      .master("local")//设置master
      .enableHiveSupport() //使用hive
      .getOrCreate()

    val sc=spark.sparkContext;
    sc.textFile("")
    var numbers=1 to 100
    numbers.reduce(_+_)

  }


}
