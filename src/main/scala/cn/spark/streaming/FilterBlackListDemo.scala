package cn.spark.streaming

import cn.spark.util.SparkUtils

/**
  * 【黑名单过滤demo】
  */
object FilterBlackListDemo {

  def main(args: Array[String]): Unit = {

    val ssc = SparkUtils.getLocalStreamingContext("FilterBlackListDemo", 8)
    //实例出一个黑名单列表
    val blacklist = Array(("hadoop", true), ("spark", true), ("mapreduce", false))
    val blackRdd = ssc.sparkContext.parallelize(blacklist)

    //接受streaming流数据  流入的数据格式:日期 名称
    val socketStreaming = ssc.socketTextStream("localhost", 9999)

    //转换：日期 名称  ====>  名称 （日期,名称）
    val pairList = socketStreaming.map(socketLine => {
      (socketLine.split(" ")(1), socketLine)
    })

    //黑名单过滤
    val validResultData = pairList.transform(transformfunc => {
      //通过左关联关联到黑名单数据  (java,(777 java,None))
      val joinedBlackRdd = transformfunc.leftOuterJoin(blackRdd)
      //过滤到黑名单中为false的数据
      val validRdd = joinedBlackRdd.filter(joinBlack => {
        joinBlack._2._2.getOrElse(true)
      })
      //返回正常数据的名称字段
      val resultRdd = validRdd.map(item => {
        item._1
      })
      resultRdd
    })
    //打印数据
    validResultData.print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()

  }

}
