package com.lc.test.baseknowlege.date;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @desc date中涉及到时区的操作测试
 * @author wlc
 * @date 2020-06-10 11:47:43
 */
public class DateTimeZoneTest {

	/**
	 * 带时区的时间，转换成毫秒数
	 */
	@Test
	public void timeZoneToMillis(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
		String timeStr = "2020-6-10 11:51:51";
		try {
			//打印的是时间戳转换为北京时间就是 2020-6-10 10:51::51,比北京时间早1个小时
			System.out.println("sf.parse(timeStr).getTime() = " + sf.parse(timeStr).getTime());
			System.out.println("System.currentTimeMillis() = " + System.currentTimeMillis());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}



}
