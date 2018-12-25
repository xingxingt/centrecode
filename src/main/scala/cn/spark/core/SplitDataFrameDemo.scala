package cn.spark.core

import cn.spark.util.SparkUtils
import org.apache.spark.sql.{Dataset, Row}

object SplitDataFrameDemo {

  def main(args: Array[String]): Unit = {


    val spark = SparkUtils.getLocalSparkSession("RandomSplitDemo")
    val sc = spark.sparkContext
    val tableDF = spark.createDataFrame(Seq(
      (0, "Hi I heard about Spark"),
      (1, "I wish Java could use case classes"),
      (2, "localhost, executor driver"),
      (3, "partition 1, PROCESS_LOCAL"),
      (4, "Adding task set 3.0 with 1 tasks"),
      (5, "show at SplitDataFrameDemo"),
      (6, "Block broadcast_3_piece0 stored as bytes"),
      (7, "Logistic regression models are neat"),
      (8, "test"),
      (9, "spark util")
    )).toDF("id", "sentence")

    tableDF.show(false)
    println("------------Method One:")

    /**
      * withReplacement：元素可以多次抽样(在抽样时替换)
      * fraction: 每个元素被抽到的概率
      */
    val ds: Dataset[Row] = tableDF.sample(false, 0.5)
    ds.createOrReplaceTempView("test_view")
    spark.sql("select * from test_view").show(false)

    println("------------Method Two:")

    /**
      * 根据权重split一个Dataframe
      */
    val splitDsArray: java.util.List[Dataset[Row]] = tableDF.randomSplitAsList(Array[Double](3.0, 3.0, 4.0), 5)
    //java集合转scala集合类型、
    import scala.collection.JavaConversions._
    //    for (splitDs: Dataset[Row] <- splitDsArray) {
    //      splitDs.show(false)
    //    }

    import scala.collection.JavaConverters._
    val splitList = splitDsArray.asScala;
    for (splitDs: Dataset[Row] <- splitList) {
      splitDs.show(false)
    }

    //todo 三个Dataset[Row]分别入不同的数据库

    spark.stop()

  }

}
