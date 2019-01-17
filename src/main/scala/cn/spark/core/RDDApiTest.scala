package cn.spark.core

import org.apache.spark.{SparkConf, SparkContext}

object RDDApiTest {

  def main(args: Array[String]): Unit = {
    var sc = getSparkContext()
    //    joinTransformation(sc)
    //    leftOuterJoinTransformation(sc)
    //    rightOuterJoinTransformation(sc)
    //    cogroup1Transformation(sc)
    //    cogroup2Transformation(sc)
    //    coalesceTransformation(sc)
    //    mapPartitionsWithIndexTransformation(sc)
    groupByKeyDemo(sc)
    reduceByKeyDemo(sc)
    reduceByKeyLocallyDemo(sc)


  }

  def getSparkContext() = {
    var sconf = new SparkConf()
    sconf.setMaster("local")
    sconf.setAppName("RDDTest")
    new SparkContext(sconf)
  }


  def joinTransformation(sc: SparkContext) = {
    var classArray = Array(Tuple2("1", "spark"), Tuple2("2", "scala"), Tuple2("3", "hadoop"), Tuple2("4", "hive"), Tuple2("5", "java"))
    var scoreArray = Array(Tuple2("1", 99), Tuple2("2", 90), Tuple2("3", 89), Tuple2("4", 90))
    var classRdd = sc.parallelize(classArray)
    var scoreRdd = sc.parallelize(scoreArray)
    var joinRdd = classRdd.join(scoreRdd)
    joinRdd.collect().foreach(item => println(s"join:${item._1}----${item._2}"))
  }

  def leftOuterJoinTransformation(sc: SparkContext) = {
    var classArray = Array(Tuple2("1", "spark"), Tuple2("2", "scala"), Tuple2("3", "hadoop"))
    var scoreArray = Array(Tuple2("1", 99), Tuple2("2", 90), Tuple2("3", 89), Tuple2("4", 90))
    var classRdd = sc.parallelize(classArray)
    var scoreRdd = sc.parallelize(scoreArray)
    var joinRdd = classRdd.leftOuterJoin(scoreRdd)
    joinRdd.collect().foreach(item => println(s"leftOuterJoin:${item._1}----${item._2}"))
  }

  def rightOuterJoinTransformation(sc: SparkContext) = {
    var classArray = Array(Tuple2("1", "spark"), Tuple2("2", "scala"), Tuple2("3", "hadoop"), Tuple2("4", "hive"))
    var scoreArray = Array(Tuple2("1", 99), Tuple2("2", 90), Tuple2("3", 89), Tuple2("4", 90), Tuple2("5", 90))
    var classRdd = sc.parallelize(classArray)
    var scoreRdd = sc.parallelize(scoreArray)
    var joinRdd = classRdd.rightOuterJoin(scoreRdd)
    joinRdd.collect().foreach(item => println(s"rightOuterJoin:${item._1}----${item._2}"))
  }

  /*传入一个RDD*/
  def cogroup1Transformation(sc: SparkContext) = {
    var rdd1 = sc.makeRDD(Array(("A", "1"), ("B", "2"), ("C", "3")), 2)
    var rdd2 = sc.makeRDD(Array(("A", "a"), ("A", "5"), ("C", "c"), ("D", "d")), 2)
    var rdd3 = rdd1.cogroup(rdd2)
    rdd3.collect.foreach(item => println(s"cogroup1:${item._1}----${item._2}"))
  }

  /*传入两个RDD*/
  def cogroup2Transformation(sc: SparkContext) = {
    var rdd1 = sc.makeRDD(Array(("A", "1"), ("B", "2"), ("C", "3")), 2)
    var rdd2 = sc.makeRDD(Array(("A", "a"), ("C", "c"), ("D", "d")), 2)
    var rdd3 = sc.makeRDD(Array(("A", "A"), ("E", "E"), ("F", "h")), 2)
    var rdd4 = rdd1.cogroup(rdd2, rdd3)
    rdd4.collect.foreach(item => println(s"cogroup2:${item._1}----${item._2}"))
  }

  /*rdd重分区*/
  def coalesceTransformation(sc: SparkContext) = {
    var rdd1 = sc.makeRDD(Array(("A", "1"), ("B", "2"), ("C", "3")), 2)
    var rdd2 = rdd1.coalesce(1, false)
    var rdd3 = rdd1.coalesce(4, true)
    println(rdd3.partitions.size)
  }

  /**/
  def mapPartitionsWithIndexTransformation(sc: SparkContext) = {
    var rdd1 = sc.makeRDD(1 to 5, 2)
    var rdd2 = rdd1.mapPartitionsWithIndex {
      (x, iter) => {
        var result = List[String]()
        var i = 0
        while (iter.hasNext) {
          i += iter.next()
        }
        result.::(x + "|" + i).iterator
      }
    }
    rdd2.collect().foreach(item => println(item))

  }

  //该函数用于将RDD[K,V]中每个K对应的V值，合并到一个集合Iterable[V]中，
  def groupByKeyDemo(sc: SparkContext) = {
    var rdd1 = sc.makeRDD(Array(("A", 0), ("A", 2), ("B", 1), ("B", 2), ("C", 1)))
    //参数numPartitions用于指定分区数；
    //参数partitioner用于指定分区函数
    val rdd2 = rdd1.groupByKey(1)
    rdd2.collect().foreach(item => println(item))
  }

  //该函数用于将RDD[K,V]中每个K对应的V值根据映射函数来运算。
  def reduceByKeyDemo(sc: SparkContext): Unit = {
    var rdd1 = sc.makeRDD(Array(("A", 0), ("A", 2), ("B", 1), ("B", 2), ("C", 1)))
    //参数numPartitions用于指定分区数；
    //参数partitioner用于指定分区函数
    var rdd2 = rdd1.reduceByKey(new org.apache.spark.HashPartitioner(2), (x, y) => x + y)
    rdd2.collect().foreach(item => println(item))

  }

  //该函数将RDD[K,V]中每个K对应的V值根据映射函数来运算，运算结果映射到一个Map[K,V]中，而不是RDD[K,V]。
  def reduceByKeyLocallyDemo(sc: SparkContext): Unit = {
    val rdd1 = sc.makeRDD(Array(("A", 0), ("A", 2), ("B", 1), ("B", 2), ("C", 1)))
    val rdd2 = rdd1.reduceByKeyLocally((x, y) => x + y)
    rdd2.foreach(item => println(item))

  }


}
