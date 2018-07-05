import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 计算topN
  */
object TopN {

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    val sc = getSparkContext()
    val lines = sc.textFile("F:\\dataTest\\topN.txt")
    val pairLines = lines.map(item => (item.toInt, item))
    val sortLines = pairLines.sortByKey(false)
    val sortResult = sortLines.map(item => item._2)
    val takeResult = sortResult.take(5)
    takeResult.foreach(println)


  }

  def getSparkContext() = {
    var sconf = new SparkConf()
    sconf.setMaster("local")
    sconf.setAppName("TopN")
    new SparkContext(sconf)
  }


}
