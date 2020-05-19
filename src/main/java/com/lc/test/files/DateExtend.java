package com.lc.test.files;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wlc
 * @desc
 * @date 2020-03-05 10:54:57
 */
public class DateExtend {

	public static String getDate(String pattern){
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		return sf.format(date);
	}

}
