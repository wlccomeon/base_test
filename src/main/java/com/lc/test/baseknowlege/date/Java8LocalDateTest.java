package com.lc.test.baseknowlege.date;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wlc
 * @desc
 * @date 2020-10-10 11:22:14
 */
public class Java8LocalDateTest {
	public static void main(String[] args) {

		System.out.println("getLocalDateYearWeekStr(getNowLocalDate()) = " + getLocalDateYearWeekStr(getNowLocalDate()));
		System.out.println("convertMillisToLocalDateTime(1602662249344L) = " + convertMillisToLocalDateTime(1602662249344L,""));
//		long nowMillis = System.currentTimeMillis();
//		System.out.println("localDate = " + convertMillisToLocalDate(nowMillis));
//
//		LocalDate today = LocalDate.now();
//		//判断指定日期是否在本周内
//		System.out.println("isLocalDateBelongCurrentWeek(today) = " + isLocalDateBelongCurrentWeek(today));

//获取当前时间
//		LocalDate currentDate = getNowLocalDate();
//		//获取年份
//		int year = currentDate.getYear();
//		System.out.println("获取当前年份:" + year);
//		//获取月份
//		int month = currentDate.getMonthValue();
//		System.out.println("获取当前月份:" + month);
//		//获取当前日期所在周的第几天
//		int weekDay = currentDate.getDayOfWeek().getValue();
//		System.out.println("获取当前日期所在周的第几天:" + weekDay);
//		//当前周所在月的第几周,第一个参数代表周是按周几开始，第二个是限定第一个自然周最少要几天，weekOfMOnth()则为获取所在月份第几周
//		int weekYear = currentDate.get(WeekFields.of(DayOfWeek.MONDAY, 1).weekOfMonth());
//		System.out.println("当前周所在月的第几周 : " + weekYear);
//
//		//获取当前时间第X周
//        /*
//        public static WeekFields of​(DayOfWeek firstDayOfWeek, int minimalDaysInFirstWeek)
//        从第一天和最小日期获得WeekFields的实例。
//        第一天的每周定义ISO DayOfWeek ，即一周中的第一天。 第一周的最小天数定义一个月或一年中必须存在的天数，从第一天开始，在将一周计算为第一周之前。 值1将计算作为第一周的一部分的月或年的第一天，而值7将要求整个七天在新的月或年中。
//
//        WeekFields实例是单例; 对于firstDayOfWeek和minimalDaysInFirstWeek的每个唯一组合，将返回相同的实例。
//
//        参数
//        firstDayOfWeek - 一周的第一天，不是null
//        minimalDaysInFirstWeek - 第一周的最小天数，从1到7
//         */
//		WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY,1);
//		int weeks = currentDate.get(weekFields.weekOfYear());
//		System.out.println("获取当前时间第" + weeks + "周");

	}

	@Test
	public void BooleanTest(){
		Boolean a = true && false;
		Boolean b = false && false;
		Boolean c = true && true;

		System.out.println("a = " + a);
		System.out.println("b = " + b);
		System.out.println("c = " + c);

	}

	@Test
	public void calcDaysBetweenLocalDate(){
		LocalDate today = getNowLocalDate();
		System.out.println("today = " + today.toString());
		LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
//		today = today.plusDays(6);
//		System.out.println("today.plus(6) = " + today);
//		System.out.println("today.toEpochDay()-today.toEpochDay() = " + (ChronoUnit.DAYS.between(monday, today)));
//		System.out.println("LocalDateTime.now() = " + LocalDateTime.now(ZoneOffset.ofHours(8)));

//		List<LocalDate> dates = Stream.iterate(monday, date -> date.plusDays(1))
//				.limit(ChronoUnit.DAYS.between(monday, today))
//				.collect(Collectors.toList());
//		dates.forEach(System.out::println);
		getDatesStrBetweenTwoDates(monday,today,"yyyyMMdd").forEach(System.out::println);
	}

	/**
	 * 将时间戳转换为北京时区所在的日期+时间
	 * @param millis 毫秒级时间戳
	 * @return
	 */
	public static String convertMillisToLocalDateTime(long millis,String formatStr){
		//北京时区+8
		LocalDateTime localDateTime = Instant.ofEpochMilli(millis).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
		DateTimeFormatter formatter = null;

		if (StringUtils.isNotBlank(formatStr)){
			formatter = DateTimeFormatter.ofPattern(formatStr);
		}else{
			formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		}
		return localDateTime.format(formatter);
	}

	/**
	 * 获取指定日期的年第几周(格式：202042)字符串
	 * @param localDate
	 * @return
	 */
	public static String getLocalDateYearWeekStr(LocalDate localDate){
		StringBuilder sb = new StringBuilder();
		sb.append(localDate.getYear());
		sb.append(localDate.get(WeekFields.of(DayOfWeek.MONDAY, 1).weekOfYear()));
		return sb.toString();
	}

	/**
	 * 获取日期字符串
	 * @param localDate 日期
	 * @param formatStr 格式化类型
	 * @return
	 */
	public static String getLocalDateStr(LocalDate localDate,String formatStr){
		if (StringUtils.isNotBlank(formatStr)){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatStr);
			return localDate.format(formatter);
		}
		return localDate.toString();
	}

	/**
	 * 获取两个日期之间(包含起止日期)的所有指定格式的日期字符串，注意：需确保end>=start
	 * @param start      开始日期
	 * @param end        结束日期
	 * @param formatStr  格式化
	 * @return
	 */
	public static List<String> getDatesStrBetweenTwoDates(LocalDate start,LocalDate end,String formatStr){

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatStr);
		List<String> result = new ArrayList<>();
		//若两个日期相等，返回其中一个即可
		if (start.isEqual(end)){
			if (StringUtils.isNotBlank(formatStr)){
				result.add(start.format(formatter));
			}else{
				result.add(start.toString());
			}
			return result;
		}
		//不等则计算并返回集合
		if (StringUtils.isNotBlank(formatStr)){
			result = Stream.iterate(start, date -> date.plusDays(1))
					.limit(ChronoUnit.DAYS.between(start, end))
					.map(localDate -> localDate.format(formatter))
					.collect(Collectors.toList());
		}else{
			result = Stream.iterate(start, date -> date.plusDays(1))
					.limit(ChronoUnit.DAYS.between(start, end))
					.map(localDate -> localDate.toString())
					.collect(Collectors.toList());
		}
		//添加上最后的那个日期
		result.add(end.format(formatter));
		return result;
	}

	/**
	 * 获取北京时区下的当前日期
	 * @return
	 */
	public static LocalDate getNowLocalDate(){
		return LocalDate.now(ZoneOffset.ofHours(8));
	}

	/**
	 * 将时间戳转换为北京时区所在的日期
	 * @param millis 毫秒级时间戳
	 * @return
	 */
	public static LocalDate convertMillisToLocalDate(Long millis){
		//北京时区+8
		return Instant.ofEpochMilli(millis).atZone(ZoneOffset.ofHours(8)).toLocalDate();
	}

	/**
	 * 判断指定时间戳是否属于当前周
	 * @param millis 毫秒级时间戳
	 * @return
	 */
	public static boolean isLocalDateBelongCurrentWeek(Long millis){
		LocalDate localDate = convertMillisToLocalDate(millis);
		return isLocalDateBelongCurrentWeek(localDate);
	}

	/**
	 * 判断指定LocalDate是否属于当前周
	 * @param localDate jdk8类型的date
	 * @return
	 */
	public static boolean isLocalDateBelongCurrentWeek(LocalDate localDate){
		LocalDate today = LocalDate.now(ZoneOffset.ofHours(8));
		//获取本周周一日期和周日日期
		LocalDate monday = today.with(TemporalAdjusters.previousOrSame( DayOfWeek.MONDAY));
		LocalDate sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY ));
		return localDate.compareTo(sunday)<=0 && localDate.compareTo(monday)>=0;
	}

	@Test
	public void test22(){
		//方法三:LocalDateTime和ChronoUnit为1.8新增
		long millSeconds = getDayLeftMillis();
		long seconds = millSeconds/1000;
		System.out.println("当天剩余毫秒3：" + millSeconds);
		System.out.println("当天剩余秒3：" + seconds);
		System.out.println("当前剩余小时："+seconds/60/60);

		LocalDateTime today = LocalDateTime.now();
		LocalDateTime sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
		LocalDateTime weekLeft = sunday.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
		long weekLeftMills = ChronoUnit.MILLIS.between(LocalDateTime.now(),weekLeft);
		System.out.println("weekLeftMills = " + weekLeftMills);
		System.out.println("weekLeftMills/1000/60/60 = " + weekLeftMills / 1000 / 60 / 60/24);



		BigDecimal weeklyTotal = new BigDecimal(getWeekLeftMillis());
		BigDecimal factor = new BigDecimal(1000 * 60 * 60 * 24);
		BigDecimal result = weeklyTotal.divide(factor, BigDecimal.ROUND_HALF_UP, 2);
		System.out.println("result = " + result);
	}

	/**
	 * 获取当前时间距当日结束时间的毫秒数
	 * @return
	 */
	public static Long getDayLeftMillis(){
		LocalDateTime nowTime = getLocalDateTime();
		LocalDateTime midnight = nowTime.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
		return ChronoUnit.MILLIS.between(LocalDateTime.now(),midnight);
	}
	/**
	 * 获取当前时间距当周结束时间的毫秒数
	 * @return
	 */
	public static Long getWeekLeftMillis(){
		LocalDateTime nowTime = getLocalDateTime();
		LocalDateTime sunday = nowTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
		LocalDateTime weekLeft = sunday.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
		return ChronoUnit.MILLIS.between(nowTime,weekLeft);
	}

	/**
	 * 获取北京时区下的当前时间
	 * @return
	 */
	public static LocalDateTime getLocalDateTime(){
		return LocalDateTime.now(ZoneOffset.ofHours(8));
	}



}
