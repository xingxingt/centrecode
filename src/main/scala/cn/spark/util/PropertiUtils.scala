package cn.spark.util

import java.util.Properties

/**
  * 配置文件工具
  * Created by acer on 2017/7/10.
  */
object PropertiUtils {

  val LOGGER = Log4j2Logger.getLogger(PropertiUtils.toString)

  private val pro = new Properties();

  /**
    * 初始化配置文件
    * @param propertyFileName 配置文件名称
    * @return 配置文件对象
    */
  def init(propertyFileName: String): Properties = {
    try {
      val inputStream = PropertiUtils.getClass.getClassLoader.getResourceAsStream(propertyFileName)
      pro.load(inputStream)
      pro
    } catch {
      case ex: Exception =>
         LOGGER.error("加载配置异常==>{}",ex)
        throw new RuntimeException("加载配置异常", ex)
    }
  }


  /**
    * 获得mysql 连接配置
    * @return
    */
  def getMysqlProp(pro:Properties) = {
    try{
      val prop = new Properties()
      prop.put("user", pro.getProperty("mysql.user")) //表示用户名是root
      prop.put("password", pro.getProperty("mysql.pwd")) //表示密码是hadoop
      prop.put("driver",pro.getProperty("mysql.driver")) //表示驱动程序是com.mysql.jdbc.Driver
      prop
    }catch {
      case ex:Exception =>
        LOGGER.error("获取mysql连接配置异常==》{}",ex)
        throw new RuntimeException("加载配置异常", ex)
    }
  }

}
