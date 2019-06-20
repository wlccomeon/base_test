package com.lc.test.thread.lock.demo.countdownlatch;

import com.lc.test.baseknowlege.forloop.ForEach;
import lombok.Getter;

/**
 * @Description:
 * @author: wlc
 * @date: 2019/6/20 0020 18:01
 **/
public enum CountriesEnum {

	QI(1,"齐"),
	CHU(2,"楚"),
	YAN(3,"燕"),
	HAN(4,"韩"),
	ZHAO(5,"赵"),
	WEI(6,"魏");

	@Getter
	private int code;
	@Getter
	private String value;

	CountriesEnum(int code, String value){
		this.code = code;
		this.value = value;
	}

	/**
	 * 根据code获取value值
	 * @return
	 */
	public static String getValue(int code){
		CountriesEnum[] countriesEnums = CountriesEnum.values();
		for (CountriesEnum countriesEnum : countriesEnums) {
			if (code==countriesEnum.getCode()){
				return countriesEnum.getValue();
			}
		}
		return null;
	}

}
