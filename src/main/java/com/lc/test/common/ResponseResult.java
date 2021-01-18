package com.lc.test.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wlc
 * @desc
 * @date 2020-06-15 16:01:02
 */
@Data
public class ResponseResult <T> implements Serializable {

	private int code;
	private String msg;
	private T data;
	private boolean success;

	private ResponseResult(int code,boolean success){
		this.code=code;
		this.success = success;
	}

	private ResponseResult(int code, String msg,boolean success){
		this.code=code;
		this.msg = msg;
		this.success = success;
	}

	private ResponseResult(int code, String msg, T data,boolean success){
		this.code = code;
		this.msg=msg;
		this.data=data;
		this.success=success;
	}

	private ResponseResult(int code, T data,boolean success){
		this.code = code;
		this.data = data;
		this.success = success;
	}

	public static <T> ResponseResult<T> createBySuccess(){
		return new ResponseResult<T>(ResponseCode.SUCCESS.getCode(),true);
	}

	/**
	 * 操作成功，没有达到预期，按照给定code进行返回
	 * @param busiCode 业务code
	 */
	public static <T> ResponseResult<T> createBySuccessCode(int busiCode){
		return new ResponseResult<T>(busiCode,true);
	}

	public static <T> ResponseResult<T> createBySuccessMessage(String msg){
		return new ResponseResult<T>(ResponseCode.SUCCESS.getCode(),msg,true);
	}

	public static <T> ResponseResult<T> createBySuccess(T data){
		return new ResponseResult<T>(ResponseCode.SUCCESS.getCode(),data,true);
	}

	public static <T> ResponseResult<T> createBySuccess(String msg, T data){
		return new ResponseResult<T>(ResponseCode.SUCCESS.getCode(),msg,data,true);
	}


	public static <T> ResponseResult<T> createByError(){
		return new ResponseResult<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc(),false);
	}


	public static <T> ResponseResult<T> createByErrorMessage(String errorMessage){
		return new ResponseResult<T>(ResponseCode.ERROR.getCode(),errorMessage,false);
	}

	public static <T> ResponseResult<T> createByErrorCodeMessage(int errorCode, String errorMessage){
		return new ResponseResult<T>(errorCode,errorMessage,false);
	}
}
