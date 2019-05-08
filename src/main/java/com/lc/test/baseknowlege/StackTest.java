package com.lc.test.baseknowlege;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class StackTest {

	public static void main(String[] args) {
		Stack<String> myStack = new Stack<String>();
		Map<String,Integer> myMap = new HashMap<String, Integer>();
		myMap.put("num",5);
		myStack = getNames(myMap,myStack);
		String result = StringUtils.EMPTY;
		while (!myStack.empty()){
			result = result + myStack.pop();
		}
		System.out.println("栈中的数据为空："+myStack.empty());
		System.out.println("打印压栈的数据为："+result);
	}

	public static Stack<String> getNames(Map<String,Integer> originMap,Stack<String> originStack){
		Integer num = originMap.get("num");
		//从5到9
		if (num<10){
			originStack.push(num+"");
			originMap.put("num",num+1);
			getNames(originMap,originStack);
		}
		return originStack;
	}
}
