package com.lc.test.baseknowlege.date;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @author wlc
 * @desc
 * @date 2020-10-10 11:22:14
 */
public class Java8LocalDateTest {
	public static void main(String[] args) {
		long nowMillis = System.currentTimeMillis();
		System.out.println("localDate = " + convertMillisToLocalDate(nowMillis));

		LocalDate today = LocalDate.now();
		//判断指定日期是否在本周内
		System.out.println("isLocalDateBelongCurrentWeek(today) = " + isLocalDateBelongCurrentWeek(today));


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
		LocalDate today = LocalDate.now();
		//获取本周周一日期和周日日期
		LocalDate monday = today.with(TemporalAdjusters.previousOrSame( DayOfWeek.MONDAY));
		LocalDate sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY ));
		return localDate.compareTo(sunday)<=0 && localDate.compareTo(monday)>=0;
	}

}
