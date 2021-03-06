package com.lc.test.baseknowlege.date;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import	java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

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


}
