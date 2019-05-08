package com.lc.test.baseknowlege;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Date转换等测试
 */
public class DateTest {

	public static void main(String[] args) {
		date2Mills();
	}

	public static void date2Mills(){
		Date now = new Date();
		System.out.println("now:"+now.getTime());
	}



}
