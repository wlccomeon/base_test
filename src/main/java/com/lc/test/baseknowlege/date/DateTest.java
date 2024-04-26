package com.lc.test.baseknowlege.date;

import com.lc.test.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Date转换等测试类
 */
public class DateTest {

	//1.前端已经限制了，不可能出现修改时间为28号之后的，所以还款计划对于2月份的不用特殊处理
	//2.注意新的还款日期，需要将年也带上，防止出现问题
	//3.----对于还款计划，直接通过日相加的方法是不同的。因为需要保证每个月的还款日相同。2019-9-16新添加----
	/**旧的还款日期*/
	public static final String  originDate = "2019-12-25 17:58:34";
	/**新的还款日期*/
	public static final String modifyDate = "2020-01-02 19:30:01";
	public static final String  datePattern = "yyyy-MM-dd HH:mm:ss";

	@Test
	public void testDateTime(){
		// 2019/08/07/ 新开奖池周期
		long now = System.currentTimeMillis();
		long todayStart = now - now % DateUtils.MILLIS_PER_DAY;
		//东八区新的一天的零点需要加1天减去8个小时
		long todayStartLocal = todayStart + DateUtils.MILLIS_PER_DAY - DateUtils.MILLIS_PER_HOUR * 8;
	}

	@Test
	public void testDatePeroid(){
		DateTime dt1 = new DateTime(2019, 12, 25, 17, 58);
		DateTime dt2 = new DateTime(2020, 01, 02, 19, 30);
		Interval interval = new Interval(dt1.getMillis(), dt2.getMillis());
		Period p = interval.toPeriod();
		System.out.println("years:" + p.getYears() + ";months:" + p.getMonths()
				+ ";days:" + p.getDays() + ";hours:" + p.getHours()
				+ ";minutes:" + p.getMinutes() + ";seconds:" + p.getSeconds()
				+ ";mills:" + p.getMillis());
	}

	/**
	 * 获取两个日期之间的月数，不够1月的按0。即向下取整，去掉小数。
	 */
	@Test
	public void testMonthsBetweenYears(){
		DateTime dt1 = new DateTime(2006, 03, 15, 17, 58);
		DateTime dt2 = new DateTime(2024, 03, 07, 19, 30);
		DateTime begin = new DateTime(dt1, DateTimeZone.forID("+08:00"));
		DateTime end = new DateTime(dt2, DateTimeZone.forID("+08:00"));
		Period period = new Period(begin, end, PeriodType.months());
		System.out.println("period.getMonths() = " + period.getMonths());
	}

	@Test
	public void testFormat(){
		Date a = testGetYearMonth("11010120000728360X");
		System.out.println("a = " + a);
		Date b = testGetYearMonth("513701930509101");
		System.out.println("b = " + b);
	}
	public Date testGetYearMonth(String idNO){
		if (StringUtils.isBlank(idNO) || (idNO.length() != 15 && idNO.length() != 18)){
			return null;
		}
		try {
			String subStr = null;
			//15位身份证截取7到12位字符
			//新身份证截取第7到14位字符
			if (idNO.length() == 15){
				subStr = "19"+idNO.substring(6, 12);
			}else{
				subStr = idNO.substring(6, 14);
			}
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			return sf.parse(subStr);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Test
	public void getPeriod(){
		DateTime begin = new DateTime(2019, 12, 25, 17, 58);
		DateTime end = new DateTime(2008, 06, 02, 19, 30);
		Period period = new Period(begin, end, PeriodType.months());
		System.out.println("period.getMonths() = " + Math.abs(period.getMonths()));
	}

	/**
	 * 增加1年，并判断是否出现诸如2023年2月29日的异常情况
	 */
	@Test
	public void testAddDate(){
		//String strTime = "2024-02-29 17:32:05";
		String strTime = "2023-02-28 17:32:05";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = simpleDateFormat.parse(strTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		//获取时间加一年或加一月或加一天
		Calendar cal = Calendar.getInstance();
		cal.setTime(date); //设置起时间
		cal.add(Calendar.YEAR, 1); //增加一年
		//cal.add(Calendar.DATE, n); //增加一天
		//cal.add(Calendar.DATE, -10); //减10天
		//cal.add(Calendar.MONTH, n); //增加一个月

		String strTime2 = simpleDateFormat.format(cal.getTime());
		System.out.println("结果为： " + strTime2);
	}

	@Test
	public void testObj() throws Exception{

		User user = new User();
		user.setAddress("AA");
		String address = user.getAddress();
		System.out.println("address = " + address);
		System.out.println("user.getAddress() = " + user.getAddress());
		User newUser = new User();
		newUser.setAddress("newUser AA");
		//newUser覆盖掉user
		BeanUtils.copyProperties(newUser,user);
		System.out.println("================");
		System.out.println("address = " + address);
		System.out.println("user.getAddress() = " + user.getAddress());
	}

	/**
	 * 计算两个日期之间相差的天数
	 */
	@Test
	public void testDateFormat(){
		DateTimeFormatter format = DateTimeFormat.forPattern(datePattern);
		DateTime originDateTime = DateTime.parse(originDate, format);
		DateTime modifyDateTime = DateTime.parse(modifyDate,format);
		if (originDateTime.getMillis()>modifyDateTime.getMillis()){
			System.out.println("原时间不能大于修改的时间！");
		}

		//计算两个日期之间相差的天数
		Period p = new Period(originDateTime, modifyDateTime, PeriodType.days());
		int days = p.getDays();
		//计算结果为8，如果modifyDate为2020-01-02 10:30:01，则结果为7
		System.out.println(days);
		System.out.println(originDateTime.plusDays(days).toDate());

		System.out.println("originDateTime day-->>"+originDateTime.get(DateTimeFieldType.dayOfMonth()));
		System.out.println("originDateTime getDayOfMonth-->>"+originDateTime.getDayOfMonth());
		System.out.println("originDateTime getDayOfWeek-->>"+originDateTime.getDayOfWeek());
		System.out.println("originDateTime getDayOfYear-->>"+originDateTime.getDayOfYear());

		DateTime dateTime = new DateTime(new Date());

		//如果大于原始日期，则添加
		if (dateTime.getDayOfMonth()>originDateTime.getDayOfMonth()){

		}
	}

	/**
	 * 获取特定时间点
	 */
	@Test
	public void getUniqueTime() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		Date start = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.SECOND, -1);

		Date end = calendar.getTime();

		System.out.println(start);
		System.out.println(end);

		/* other way */
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		start = formater2.parse(formater.format(new Date())+ " 00:00:00");
		end = formater2.parse(formater.format(new Date())+ " 23:59:59");

		System.out.println(start);
		System.out.println(end);

		Date nowDate = new Date();
		long mills = end.getTime() - nowDate.getTime();
		long seconds =mills /1000;
		long minutes = mills/1000/60;
		long hours = mills/1000/60/60;
		System.out.println("mills->>"+mills+"\n seconds-->>"+seconds+" \n minutes-->>"+minutes+"\n hours-->>"+hours);

	}

	@Test
	public void convertMillisToLocalDate(){
		//2021-04-23 00:00:00
		Long startMillis = 1619107200000L;
		//2021-04-23 00:00:00
		Long endMillis = 1619712000000L;
		LocalDate startLocalDate = Instant.ofEpochMilli(startMillis).atZone(ZoneOffset.ofHours(8)).toLocalDate();
		LocalDate endLocalDate = Instant.ofEpochMilli(startMillis).atZone(ZoneOffset.ofHours(8)).toLocalDate();
	}

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		int dow = cal.get(Calendar.DAY_OF_WEEK);
		int dom = cal.get(Calendar.DAY_OF_MONTH);
		int dowim = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		int doy = cal.get(Calendar.DAY_OF_YEAR);
		int woy = cal.get(Calendar.WEEK_OF_MONTH);

		System.out.println("当期时间: " + cal.getTime());
		System.out.println("日期: " + day);
		System.out.println("月份: " + month);
		System.out.println("年份: " + year);
		System.out.println("一周的第几天: " + dow);  // 星期日为一周的第一天输出为 1，星期一输出为 2，以此类推
		System.out.println("一周的第几天2: " + dowim);
		System.out.println("一月中的第几天: " + dom);
		System.out.println("一年的第几天: " + doy);
		System.out.println("一个月中的第几周:"+woy);
	}


	/**
	 * 时间字符串转为指定格式和时区的时间字符串
	 * @param originTimeStr  原时间字符串
	 * @param originFormat   原时间字符串格式
	 * @param format         将要转换成的时间字符串格式
	 * @param timeZone       时区
	 * @return yyy-d
	 */
	public static String getDateStr(String originTimeStr,String originFormat, String format,TimeZone timeZone) {
		String result = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat(originFormat);
			Date date = df.parse(originTimeStr);
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			dateFormat.setTimeZone(timeZone == null ? TimeZone.getTimeZone("GMT+8") : timeZone);
			result = dateFormat.format(date);
		}catch (Exception e){
			e.printStackTrace();
//			log.error("转换日期出错,参数originTimeStr:{},originFormat:{},format:{},timeZone:{}",originTimeStr,originFormat,format,timeZone);
		}
		return result;
	}

	@Test
	public void test(){
		Boolean a = null;
		int b = 0;
		if (b == 0 && a){
			System.out.println("aaaa");
		}
	}

}
