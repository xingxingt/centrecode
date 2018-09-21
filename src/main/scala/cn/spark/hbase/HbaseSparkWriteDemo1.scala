package cn.spark.hbase

import org.apache.hadoop.hbase.client.{Put, Result}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapreduce.Job
import org.apache.spark._

/**
  * 【使用saveAsNewAPIHadoopDataset写入数据】
  */
object HbaseSparkWriteDemo1 {

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "D:\\devTool\\winutils\\winutils\\hadoop-2.6.0")
    val sparkConf = new SparkConf().setAppName("HBaseTest").setMaster("local")
    val sc = new SparkContext(sparkConf)

    val tablename = "sexrate"

    sc.hadoopConfiguration.set("hbase.zookeeper.quorum", "node1,node2")
    sc.hadoopConfiguration.set("hbase.zookeeper.property.clientPort", "2181")
    sc.hadoopConfiguration.set(TableOutputFormat.OUTPUT_TABLE, tablename)

    val job = new Job(sc.hadoopConfiguration)
    job.setOutputKeyClass(classOf[ImmutableBytesWritable])
    job.setOutputValueClass(classOf[Result])
    job.setOutputFormatClass(classOf[TableOutputFormat[ImmutableBytesWritable]])

    val indataRDD = sc.makeRDD(Array("1,jack,15", "2,Lily,16", "3,mike,16"))
    val rdd = indataRDD.map(_.split(',')).map { arr => {
      val put = new Put(Bytes.toBytes(arr(0)))
      put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("man"), Bytes.toBytes(arr(1)))
      put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("women"), Bytes.toBytes(arr(2)))
      (new ImmutableBytesWritable, put)
    }
    }

    rdd.saveAsNewAPIHadoopDataset(job.getConfiguration())
  }

}
