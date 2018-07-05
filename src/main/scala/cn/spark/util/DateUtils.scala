package cn.spark.util

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDate, LocalDateTime, ZoneId}
import java.util.{Calendar, Date}

/**
  * 日期工具类
  * Created by acer on 2017/7/1.
  */
//java.time.LocalDate：LocalDate是一个不可变的类，它表示默认格式(yyyy-MM-dd)的日期，我们可以使用now()方法得到当前时间，
// 也可以提供输入年份、月份和日期的输入参数来创建一个LocalDate实例.

// java.time.LocalDateTime：LocalDateTime是一个不可变的日期-时间对象，它表示一组日期-时间，默认格式是yyyy-MM-dd-HH-mm-ss.zzz。
//LocalTime
//SQL -> Java
//--------------------------
//date -> LocalDate
//time -> LocalTime
//timestamp -> LocalDateTime

object DateUtils {

  val YYYYMMDD = "yyyyMMdd"
  val YYYY_MM_DD = "yyyy-MM-dd"


  /**
    * 日期加减
    * @param formatter 传入日期格式
    * @param date 传入日期
    * @param days 加减的日期天数 加一天 1，减一天 -1
    * @return
    */
  def getDaysBefore(formatter:String,date:String,days:Int):String= {
    var dateFormat: SimpleDateFormat = new SimpleDateFormat(formatter)
    dateFormat.parse(date)
    var cal: Calendar = Calendar.getInstance()
    cal.setTime(dateFormat.parse(date))
    cal.add(Calendar.DATE, days)
    var day = dateFormat.format(cal.getTime())
    day
  }

  /**
    * 给定毫秒数返回指定格式日期字符串
    * @param millis 时间毫秒数
    * @param formatter yyyyMMdd
    * @return
    */
  def getDateStingByMillis(millis:Long, formatter:String)={
    val ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault)
    getSpecifiedDateStr(ldt,formatter)
  }

  /**
    * 根据格式化返回指定日期
    * @param date
    * @param formatter
    * @return
    */
  def getSpecifiedDateStr(date:LocalDateTime, formatter:String):String = {
    date.format(DateTimeFormatter.ofPattern(formatter ))
  }


  /**
    *按指定格式返回当天时间字符串
    * @param formatter 格式化字符串 yyyyMMdd
    * @return y
    */
  def getCurrentDateStr(formatter:String):String = {
    val today = LocalDateTime.now()
    today.format(DateTimeFormatter.ofPattern(formatter ))
  }

  /**
    *按指定格式转换时间
    * <p>
    * 传入参数分别为 2017-07-13 ,yyyy-MM-dd
    * @param timeString  时间字符串
    * @param formatterStr formatter
    * @return 返回LocalDate
    */
   def stringToLocalDate(timeString:String,formatterStr: String):LocalDate={
      try{
        val _formatter = DateTimeFormatter.ofPattern(formatterStr)
        val localDate = LocalDate.parse(timeString, _formatter)
        localDate
      }catch {
        case ex :Exception => throw new RuntimeException("格式化异常=>",ex)
      }
  }


  /**
    * 将一种时间字符串转成另一种时间字符串
    * <p>
    * 比如 20170713  --> 2017-07-13 ,传入的参数分别为 20170713 ,yyyyMMdd,yyyy-MM-dd
    * @param source  待转换的时间字符串
    * @param soruceTimeformattor  待转换的时间字符串
    * @param targeteTimeformattor 需要装换成的字符串格式
    * @return
    */
  def dateTimeToAnoter(source:String,soruceTimeformattor:String,targeteTimeformattor:String):String={
      val localDate = LocalDate.parse(source,DateTimeFormatter.ofPattern(soruceTimeformattor))
      localDate.format(DateTimeFormatter.ofPattern(targeteTimeformattor))
  }


  /**
    * date 类型 转换任意格式时间字符串

任意格式时间字符串转换其他格式时间字符串
    */

  def main(args: Array[String]): Unit = {
    //val tmp = getSpecifiedDateStr(LocalDateTime.now().plusDays(3),"yyyyMMdd")
//    import java.time.{Instant, LocalDateTime, ZoneId}
//    val ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault)
    //    println(getSpecifiedDateStr(ldt,"yyyyMMdd"))

    //    print(stringToDateTime("20170202","yyyyMMdd"))
    //    val _formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    //    val dateTime = LocalDateTime.parse("20170202", _formatter)
    //
    //    print(dateTime)

//    import java.time.format.DateTimeFormatter
//    val str = "1986/04/08"
//    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//    //    val dateTime = LocalDateTime.parse(str, formatter)
//    //    val localDate = LocalDate.parse(str, formatter)
//    //    println(localDate.format( DateTimeFormatter.ofPattern("yyyyMMdd")))
//
//    val _localDate = LocalDate.parse(str)
//    println(_localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")))


    println(dateTimeToAnoter("20180110","yyyyMMdd","yyyy-MM-dd"))
  }

  /**
    * 获取当天日期
    * @param format 返回日期格式  (例如:yyyy-MM-dd)
    * @return
    */
  def getNowDate(format:String):String={
    var now:Date = new Date()
    var  dateFormat:SimpleDateFormat = new SimpleDateFormat(format)
    var hehe = dateFormat.format( now )
    hehe
  }


  /**
    * 获取
    * @param sourceDate   原始日期（20170801）
    * @param source_format 原始日期格式（yyyyMMdd）
    * @param target_format 返回日期格式（eg:yyyy-MM-dd）
    * @param interval  间隔天数 负数往前N 天 ，正数往后推N天
    * @return
    */
  def getIntervalDay(sourceDate:String,source_format:String,target_format:String,interval:Int):String={
    val _localDate = DateUtils.stringToLocalDate(sourceDate, source_format)
    //获取前一天日期
    val date = _localDate.plusDays(interval).format(DateTimeFormatter.ofPattern(target_format))
    date
  }





}
