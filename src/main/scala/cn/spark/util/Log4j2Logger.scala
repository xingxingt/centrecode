package cn.spark.util

/**
  * 日志工具类
  * Created by acer on 2017/6/26.
  */
object Log4j2Logger {

  /**
    * 获取Logger对象
    * @param clazz 类名
    * @return
    */
  def getLogger(clazz:String):org.apache.logging.log4j.Logger={
    val log = org.apache.logging.log4j.LogManager.getLogger(clazz)
    log
  }

}
