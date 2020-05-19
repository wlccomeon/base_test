package com.lc.test.baseknowlege.regex;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @desc  正则表达式测试
 * @author wlc
 * @date 2020-03-25 20:01:57
 */
public class RegExTest {

	@Test
	public void test1(){
		String jwtSessionStr = "JWT-SESSION=eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJBWEVRM3BaeUszQnA3UTaWF0IjoxNTg1MTI1OTU1LCJleHA";
		String xsrfTokenStr = "XSRF-TOKEN=o6ua0ej847qtiov81e1fh92ts1;";
		Matcher xsrfTokenMather = Pattern.compile("(?<=XSRF-TOKEN=).+?(?=;)").matcher(xsrfTokenStr);
		Matcher jwtSessionMather = Pattern.compile("(?<==).*").matcher(jwtSessionStr);
		if (xsrfTokenMather.find()) {
			String xsrfToken = xsrfTokenMather.group();
			System.out.println("xsrfToken = " + xsrfToken);
		}
		if (jwtSessionMather.find()) {
			String jwtSession = jwtSessionMather.group();
			System.out.println("jwtSession = " + jwtSession);
		}
	}

}
