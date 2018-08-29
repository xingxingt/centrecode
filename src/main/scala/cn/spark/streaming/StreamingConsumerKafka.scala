package cn.spark.streaming


import kafka.serializer.StringDecoder
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  *【Direct API】
  * 基于Direct的拉去数据方式，RDD的partition会对应Topic中的每个partition，
  * RDD的parition和Topic的partition是一致的，那么RDD读取的数据就是Kafka中的数据，
  * 符合数据的本地性原则，读数据和处理数据在同一个地方，从而可以极大的提高它的性能；
  * 而kafka的zero copy的优势，从而使从Kafka中获取数据的速度要比HDFS中还要快；
  * 在这个模式下SparkStreaming自己保存kafka的offset，所以可以避免出现重复消费；
  */
object StreamingConsumerKafka {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("spark_kafka").master("local[*]").getOrCreate()
    val batchDuration = Seconds(5) //时间单位为秒
    val streamContext = new StreamingContext(spark.sparkContext, batchDuration)
    streamContext.checkpoint("/Users/eric/SparkWorkspace/checkpoint")

    val topics = Array("behaviors").toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> "localhost:9092")
    val stream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](streamContext, kafkaParams, topics)
    stream.foreachRDD(rdd => {
      rdd.foreach(line => {
        println("key=" + line._1 + "  value=" + line._2)
      })
    })
    streamContext.start() //spark stream系统启动
    streamContext.awaitTermination() //


  }

}
