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


    //queryExecution
    /**
      *
      * == Parsed Logical Plan ==
          'Aggregate ['name], [unresolvedalias('sum('v), None), 'name]
          +- 'SubqueryAlias tmp
           +- 'Project ['student.id, ((100 + 10) + 'score.score) AS v#30, 'name]
              +- 'Filter (('student.id = 'score.id) && ('student.age >= 11))
                 +- 'Join Inner
                    :- 'UnresolvedRelation `student`
                    +- 'UnresolvedRelation `score`

          == Analyzed Logical Plan ==
          sum(v): bigint, name: string
          Aggregate [name#8], [sum(cast(v#30 as bigint)) AS sum(v)#32L, name#8]
          +- SubqueryAlias tmp
           +- Project [id#7, ((100 + 10) + score#24) AS v#30, name#8]
              +- Filter ((id#7 = id#22) && (age#9 >= 11))
                 +- Join Inner
                    :- SubqueryAlias student, `student`
                    :  +- Project [_1#3 AS id#7, _2#4 AS name#8, _3#5 AS age#9]
                    :     +- LocalRelation [_1#3, _2#4, _3#5]
                    +- SubqueryAlias score, `score`
                       +- Project [_1#18 AS id#22, _2#19 AS subject#23, _3#20 AS score#24]
                          +- LocalRelation [_1#18, _2#19, _3#20]

          == Optimized Logical Plan ==
          Aggregate [name#8], [sum(cast(v#30 as bigint)) AS sum(v)#32L, name#8]
          +- Project [(110 + score#24) AS v#30, name#8]
           +- Join Inner, (id#7 = id#22)
              :- Project [_1#3 AS id#7, _2#4 AS name#8]
              :  +- Filter (_3#5 >= 11)
              :     +- LocalRelation [_1#3, _2#4, _3#5]
              +- LocalRelation [id#22, score#24]

          == Physical Plan ==
              *HashAggregate(keys=[name#8], functions=[sum(cast(v#30 as bigint))], output=[sum(v)#32L, name#8])
          +- Exchange hashpartitioning(name#8, 200)
           +- *HashAggregate(keys=[name#8], functions=[partial_sum(cast(v#30 as bigint))], output=[name#8, sum#37L])
              +- *Project [(110 + score#24) AS v#30, name#8]
                 +- *BroadcastHashJoin [id#7], [id#22], Inner, BuildRight
                    :- *Project [_1#3 AS id#7, _2#4 AS name#8]
                    :  +- *Filter (_3#5 >= 11)
                    :     +- LocalTableScan [_1#3, _2#4, _3#5]
                    +- BroadcastExchange HashedRelationBroadcastMode(List(cast(input[0, int, false] as bigint)))
                       +- LocalTableScan [id#22, score#24]
      *
      *
      */


  }

}
