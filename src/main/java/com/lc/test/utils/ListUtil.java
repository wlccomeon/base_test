package com.lc.test.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @desc list 工具类
 * @author wlc
 * @date 2020-05-19 19:14:39
 */
public class ListUtil {

	/**
	 * 从list中随机抽取若干不重复元素
	 * @param paramList:被抽取list
	 * @param count:抽取元素的个数
	 * return 由抽取元素组成的新list
	 */
	public List<Long> getRandomList(List<Long> paramList, int count){
		if(count==0){
			return null;
		}
		Random random=new Random();
		//临时存放产生的list索引，去除重复的索引
		List<Integer> tempList=new ArrayList<>();
		//生成新的list集合
		List<Long> newList=new ArrayList();
		int temp=0;
		int paramSize = paramList.size();
		if (paramSize<count){
			count = paramSize;
		}
		//如果数据小于1，取一条数据
		if(count<=1){
			temp = random.nextInt(paramSize);
			newList.add(paramList.get(temp));
		}else {
			for(int i=0;i<count;i++){
				//初始化一个随机数，将产生的随机数作为被抽list的索引
				temp=random.nextInt(paramSize);
				//判断随机抽取的随机数
				if(!tempList.contains(temp)){
					tempList.add(temp);
					newList.add(paramList.get(temp));
				}
				else{
					i--;
				}
			}
		}
		return newList;
	}
}
