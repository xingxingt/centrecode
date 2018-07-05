import org.apache.spark.sql.SparkSession

object MapTest {

  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder()
      .appName("testMap")
      .master("local")//设置master
      .getOrCreate()

    val sc=spark.sparkContext

    var array = Array("hello", "world", "hello", "spark", "hello", "hive")
    var mapArray=array.map(item=>item.toUpperCase())

    for(item <- mapArray){
      println(item)
    }

    var flatArray=array.flatMap(item=>item+"00")
    for (item <- flatArray){
      println(item)
    }

  }

}
