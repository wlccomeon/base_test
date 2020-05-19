package com.lc.test.baseknowlege.random;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

/**
 * @desc 随机码：数字+字母
 * @author wlc
 * @date 2020-03-26 18:38:01
 */
public class WordNumRadom {
	/**
	 * 生成随机字符串：数字和字母组成
	 * @param length 字符串长度
	 * @return
	 */
	public String getStringRandom(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();

		for(int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			//输出字母还是数字
			if( "char".equalsIgnoreCase(charOrNum) ) {
				//输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				sb.append((char)(random.nextInt(26) + temp));
			} else if( "num".equalsIgnoreCase(charOrNum) ) {
				sb.append(random.nextInt(10));
			}
		}
		return sb.toString();
	}

	public static void  main(String[] args) {
		WordNumRadom test = new WordNumRadom();
		//测试
		System.out.println(test.getStringRandom(8));
		String filename= RandomStringUtils.randomAlphanumeric(8);
		System.out.println("filename = " + filename);
	}
}
