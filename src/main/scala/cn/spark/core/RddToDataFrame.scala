package cn.spark.core

import cn.spark.util.SparkUtils
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}

/**
  * 【RDD转DataFrame】
  */
object RddToDataFrame {

  // 注意：case class必须定义在main方法之外；否则会报错
  case class People(name: String, age: Int)

  /**
    * 【方法一】
    * 使用反射的方式，用反射去推倒出来RDD里面的schema
    * case class最多支持22个字段如果超过了22个字段，我们就必须要自己开发一个类，实现product接口才行
    *
    * @param spark
    */
  private def runInferSchemaExample(spark: SparkSession): Unit = {
    import spark.implicits._
    val rdd = spark.sparkContext.textFile("E:/大数据/data/people.txt")
    val df = rdd.map(_.split(","))
      .map(x => People(x(0), x(1).trim.toInt)) //将rdd的每一行都转换成了一个people
      .toDF //必须先导入import spark.implicits._  不然这个方法会报错
    df.show()
    df.createOrReplaceTempView("people")
    // 这个DF包含了两个字段name和age
    val teenagersDF = spark.sql("SELECT name, age FROM people WHERE age BETWEEN 13 AND 19")
    // teenager(0)代表第一个字段
    // 取值的第一种方式：index from zero
    teenagersDF.map(teenager => "Name: " + teenager(0)).show()
    // 取值的第二种方式：byName
    teenagersDF.map(teenager => "Name: " + teenager.getAs[String]("name") + "," + teenager.getAs[Int]("age")).show()
  }

  /**
    * 【方法二】
    * 创建一个DataFrame，使用编程的方式 这个方式用的非常多
    *
    * @param spark
    */
  private def runProgrammaticSchemaExample(spark: SparkSession): Unit = {
    // 1.转成RDD
    val rdd = spark.sparkContext.textFile("E:/大数据/data/people.txt")
    // 2.定义schema，带有StructType的
    // 定义schema信息
    val schemaString = "name age"
    // 对schema信息按空格进行分割
    // 最终fileds里包含了2个StructField
    val fields = schemaString.split(" ")
      // 字段类型，字段名称判断是不是为空
      .map(fieldName => StructField(fieldName, StringType, nullable = true))
    val schema = StructType(fields)
    // 3.把我们的schema信息作用到RDD上
    //   这个RDD里面包含了一些行
    // 形成Row类型的RDD
    val rowRDD = rdd.map(_.split(","))
      .map(x => Row(x(0), x(1).trim))
    // 通过SparkSession创建一个DataFrame
    // 传进来一个rowRDD和schema，将schema作用到rowRDD上
    val peopleDF = spark.createDataFrame(rowRDD, schema)
    peopleDF.show()
  }


  def main(args: Array[String]): Unit = {
    val spark = SparkUtils.getLocalSparkSession("rddToDataFrame")
    runInferSchemaExample(spark)
    runProgrammaticSchemaExample(spark)
  }

}
