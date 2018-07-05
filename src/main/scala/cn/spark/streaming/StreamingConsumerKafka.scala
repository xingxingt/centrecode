package cn.spark.streaming

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StreamingConsumerKafka {

  def main(args: Array[String]): Unit = {

    val brokers=""
    val group=""
    val topics=""
    val sparkConf = new SparkConf().setAppName("SCK")
    sparkConf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    val ssc = new StreamingContext(sparkConf, Seconds(30))

    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers, "serializer.class" -> "kafka.serializer.StringEncoder","group.id" -> group)
//    val lines = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams,topics).map(_._2)




  }

}
