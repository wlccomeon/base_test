package com.lc.test.baseknowlege;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wlc on 2018/3/6 0006.
 */
public class MainTest {
	public static void main(String[] args) {
		List<Integer> initList = new ArrayList<Integer>(2);
		List<Integer> secList = new ArrayList<Integer>(2);
		secList.add(1);
		secList.add(2);
		initList.addAll(secList);
		System.out.println("赋值之后的数据为："+JSON.toJSONString(initList));
		System.out.println("当前时间："+System.currentTimeMillis() / 1000);
	}
}
