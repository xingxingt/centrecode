package cn.spark.ml

import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object DFTest {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("DFTest")
      .master("local") //设置master
      .config("Dfile.encoding", "UTF-8")
      .getOrCreate()


    val sentenceData = spark.createDataFrame(Seq(
      (0.0, "Hi I heard about Spark"),
      (0.0, "I wish Java could use case classes"),
      (1.0, "Logistic regression models are neat")
    )).toDF("label", "sentence")

    val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
    val wordsData = tokenizer.transform(sentenceData)

    println("----------tokenizer wordsData:")
    wordsData.show()


    val hashingTF = new HashingTF()
      .setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(20)
    val featurizedData = hashingTF.transform(wordsData)
    // alternatively, CountVectorizer can also be used to get term frequency vectors

    println("----------hashingTF featurizedData:")
    featurizedData.show()


    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
    val idfModel = idf.fit(featurizedData)



    val rescaledData = idfModel.transform(featurizedData)
    rescaledData.select("label", "features").show()

  }


}
