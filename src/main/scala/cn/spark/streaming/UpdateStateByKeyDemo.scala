package cn.spark.streaming

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 【UpdateStateByKey】
  *
  * updateStateByKey的主要功能是可以随着时间的流逝在Spark Streaming中为每一个可以维护一份state状态，
  * 并且通过更新函数对该key的状态不断更新，对于每个新的batch而言，park Streaming会在使用updateStateByKey的时候为已经
  * 存在的key进行state的状态更新（对于每个新出现的key，会同样的执行state更新函数操作），
  * 但是如果更新函数对state更新后返回none的话，此时该key对应的state会被删除掉，需要特别说明的是state可以是任意类型的数据结构；
  *
  * 如果不断的更新某个key的state，那么一定会涉及到对state的存储，则需要开启checkpoint机制，
  * Notice:checkpoint只能存储能够存储在文件系统中的数据
  *
  * ref:http://sharkdtu.com/posts/spark-streaming-state.html
  *
  */
object UpdateStateByKeyDemo {


  def main(args: Array[String]): Unit = {

    // 创建Spark上下文，并以1秒内收到的数据作为一批
    val sparkConf = new SparkConf().setAppName("UpdateStateByKeyDemo").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(10))
    //todo 这里必须要开启checkpoint
    ssc.checkpoint("/Users/axing/opt/others")


    // 创建了一个DStream，从Socket中接受数据。
    val lines = ssc.socketTextStream("localhost", 9999)

    // 以空格把收到的每一行数据分割成单词
    val words = lines.flatMap(_.split(" "))

    // 在本批次内计单词的数目
    val pairs = words.map(x => (x, 1))
    //  val wordCounts = maps.reduceByKey(_ + _)
    /**
      * 这里使用updateStateByKey，以batch interval为单位来对历史state进行更新，这是一大进阶
      * 否则如果要达到相同的效果，只能将历史state数据保存在redis，hbase等组件中来完成同一个key的state更新
      */
    val wordCounts = pairs.updateStateByKey[Int](addFunc)

    // 打印每个RDD中的前10个元素到控制台
    wordCounts.print()
    ssc.start() // 启动计算
    ssc.awaitTermination() // 等待计算结束

    ssc.stop()

  }

  val addFunc = (currValues: Seq[Int], prevValueState: Option[Int]) => {
    //通过Spark内部的reduceByKey按key规约，然后这里传入某key当前批次的Seq/List,再计算当前批次的总和
    val currentCount = currValues.sum
    // 已累加的值
    val previousCount = prevValueState.getOrElse(0)
    // 返回累加后的结果，是一个Option[Int]类型
    Some(currentCount + previousCount)
  }


}
