package com.lc.test.common;

/**
 * @desc 通用响应枚举
 * @author wlc
 * @date 2020-06-15 16:01:54
 */
public enum ResponseCode {
	SUCCESS(200,"SUCCESS"),
	NEED_LOGIN(201,"未登录！"),
	ERROR(500,"系统异常!"),
	NO_AUTHORITY(403,"没有权限！"),
	NOT_FOUND(404,"路径不存在，请检查路径是否正确"),
	DATA_NULL(100,"数据为空！"),
	PARAM_ERROR(101,"参数有误！"),
	SMS_SEND_ERROR(300,"发送短信失败");

	private final int code;
	private final String desc;

	ResponseCode(int code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public int getCode(){
		return this.code;
	}

	public String getDesc(){
		return this.desc;
	}
}
