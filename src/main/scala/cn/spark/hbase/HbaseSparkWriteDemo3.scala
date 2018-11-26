//package etl.test
//
//import org.apache.hadoop.hbase.client.{ConnectionFactory, Put}
//import org.apache.hadoop.hbase.util.Bytes
//import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
//import org.apache.spark.{SparkConf, SparkContext}
//
///**
//  * spark使用ConnectionFactory操作hbase
//  */
//object HbaseSparkWriteDemo3 {
//
//  def main(args: Array[String]): Unit = {
//    System.setProperty("hadoop.home.dir", "D:\\devTool\\winutils\\winutils\\hadoop-2.6.0")
//    val sparkConf = new SparkConf().setAppName("HBaseTest").setMaster("local")
//    val sc = new SparkContext(sparkConf)
//
//    val hbaseConf = HBaseConfiguration.create()
//    hbaseConf.set("hbase.zookeeper.quorum", "node1,node2")
//    hbaseConf.set("hbase.zookeeper.property.clientPort", "2181")
//    hbaseConf.set("hbase.defaults.for.version.skip", "true")
//    val hbaseConn = ConnectionFactory.createConnection(hbaseConf)
//    val table = hbaseConn.getTable(TableName.valueOf("sexrate"))
//    val rowKey = "20180725"
//    val put = new Put(rowKey.getBytes())
//    put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("man"), Bytes.toBytes("52"))
//    put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("women"), Bytes.toBytes("49"))
//    table.put(put)
//
//    sc.stop()
//  }
//
//}
