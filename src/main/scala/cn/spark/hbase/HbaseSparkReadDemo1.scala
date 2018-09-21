package cn.spark.hbase

import org.apache.hadoop.hbase.client.HBaseAdmin
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, HTableDescriptor, TableName}
import org.apache.spark._

/**
  * 【将读取的hbase数据转化为Spark RDD】
  */
object HbaseSparkReadDemo1 {


  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "D:\\devTool\\winutils\\winutils\\hadoop-2.6.0")
    val sparkConf = new SparkConf().setAppName("HBaseTest").setMaster("local")
    val sc = new SparkContext(sparkConf)

    val tablename = "sexrate"
    val conf = HBaseConfiguration.create()
    //设置zooKeeper集群地址，也可以通过将hbase-site.xml导入classpath，但是建议在程序里这样设置
    conf.set("hbase.zookeeper.quorum", "node1,node2")
    //设置zookeeper连接端口，默认2181
    conf.set("hbase.zookeeper.property.clientPort", "2181")
    conf.set(TableInputFormat.INPUT_TABLE, tablename)

    // 如果表不存在则创建表
    val admin = new HBaseAdmin(conf)
    if (!admin.isTableAvailable(tablename)) {
      val tableDesc = new HTableDescriptor(TableName.valueOf(tablename))
      admin.createTable(tableDesc)
    }

    //读取数据并转化成rdd
    val hBaseRDD = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])

    val count = hBaseRDD.count()
    println("count------:" + count)
    hBaseRDD.foreach { case (_, result) => {
      //获取行键
      val key = Bytes.toString(result.getRow)
      //通过列族和列名获取列
      val man = Bytes.toString(result.getValue("f1".getBytes, "man".getBytes))
      val women = Bytes.toString(result.getValue("f1".getBytes, "women".getBytes))
      println("Row key:" + key + " man:" + man + " women:" + women)
    }
    }

    sc.stop()
    admin.close()

  }

}
