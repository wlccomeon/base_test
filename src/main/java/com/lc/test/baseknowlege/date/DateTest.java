package com.lc.test.baseknowlege.date;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import	java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date转换等测试类
 */
public class DateTest {

	public static final String  dateStr = "2019-9-5 17:58:34";
	public static final String  datePattern = "yyyy-MM-dd HH:mm:ss";


	@Test
	public void parseDate(){
		try {
			SimpleDateFormat sf = new SimpleDateFormat(datePattern);
			Date myDate = sf.parse(dateStr);
			System.out.println("mydate-->>>"+JSON.toJSONString(myDate));



		}catch (Exception e){
			System.out.println("date convert error ..."+e.toString());
		}
	}
}
