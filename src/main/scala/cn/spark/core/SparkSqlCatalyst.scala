package cn.spark.core

import cn.spark.util.SparkUtils
import org.apache.spark.sql.catalyst.optimizer.{PushDownPredicate, PushPredicateThroughJoin}

/**
  * 【spark sql 执行】
  * 执行步骤:Parsed Logical Plan ->Analyzed Logical Plan->Optimized Logical Plan ->Physical Plan
  * 划分的细点: sql->dataSet->Unresolved Logical Plan--Analyzer-->Analyzer Logical Plan-->withCacheData Logical Plan
  * --Logical Optimizer-->Optimized Logical Plan--Planner-->Physical Plan--Physical Optimizer-->Executable Physical Plan
  * -->RDD
  * Ref:https://mp.weixin.qq.com/s/IE-YtfMzYMVnw20JgZO97Q
  */
object SparkSqlCatalyst {

  def main(args: Array[String]): Unit = {

    val spark = SparkUtils.getLocalSparkSession("SparkSqlCatalyst")

    val stu = "student"
    val score = "score"

    spark.catalog.dropTempView(stu)
    spark.catalog.dropTempView(score)


    import spark.implicits._
    val sqlArray = Seq((1, "zhangsan", 13), (2, "lisi", 23), (3, "wangwu", 31), (4, "zhaoliu", 33), (5, "zz", 35))
    sqlArray.toDF("id", "name", "age").createOrReplaceTempView("student")

    val sqlArray2 = Seq((1, "chinese", 60), (1, "eg", 70), (1, "math", 31), (2, "chinese", 61), (2, "eg", 75), (2, "math", 31)
      , (3, "chinese", 61), (3, "eg", 75), (3, "math", 31), (4, "chinese", 61), (4, "eg", 75), (4, "math", 31)
      , (5, "chinese", 61), (5, "eg", 75), (5, "math", 31))
    sqlArray2.toDF("id", "subject", "score").createOrReplaceTempView("score")

    val queryExecution = spark.sql("select sum(v),name from (select student.id,100+10+score.score as v,name from student join " +
      "score where student.id=score.id and student.age>=11)  tmp group by name").queryExecution


    println("---------Parser")
    //展示这段sql的queryExecution
    println(queryExecution)


    println("---------Analyzer")
    //    println(ResolveRelations(queryExecution.logical))


    println("---------optimizer")
    import org.apache.spark.sql.catalyst.optimizer.ConstantFolding
    //optimizer优化  ConstantFolding 常量折叠优化
    println(ConstantFolding(queryExecution.analyzed))
    //谓词下推优化 会把filter放在join之前 从而减少数据量
    println(PushPredicateThroughJoin(queryExecution.analyzed))


  }

}
