package com.lc.test.baseknowlege;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class MapTest {


    /***
     * 测试map的containskey方法，
     * 如果key为null，只要map初始化过，结果返回为true。。。
     * 跟断点发现穿过去的key变成了诸如：file:///C:/Java/jdk1.8.0_131/jre/lib/ext/access-bridge-64.jar。
     */
    @Test
    public  void testContainsKey(){
        Map<String,Object> dataMap = new HashMap<String, Object>();
//        dataMap.put("a",1);
//        dataMap.put("b",2);
        if (dataMap.containsKey(null)){
            System.out.println("false");
        }else{
            System.out.println("true");
        }
    }

    @Test
	public void testMapReturn(){
    	Map<String,String> dataMap = new HashMap<>(2);
    	dataMap.put("1","java");
    	dataMap.put("1","python");
		String result = dataMap.put("1","ruby");
		System.out.println("result-->>"+result);
	}

	/**
	 * 测试jdk1.8的新方法----相对于jdk1.7来说
	 */
	@Test
	public void testMapNewMethod(){


			Map<Integer,String> map = new HashMap();
			map.put(1, "jack");
			map.put(2, "tom");
			map.put(3, "wawa");

			//Map接口里的默认方法介绍：
			//getOrDefault方法
			String value=map.getOrDefault(4, "null");
			System.out.println(value);
			//如果不用默认方法，自己就要写if...else判断了
//      String s = map.get(5);
//      if(s==null) {
//
//      }else {
//
//      }

			//put方法：如果key存在时，就替换值为新的值
			String v1=map.put(3, "haha");
			System.out.println(v1);

			//putIfAbsent方法:如果key存在就不替换值，如果key不存在就新存入
			String v=map.putIfAbsent(3, "wangwang");
			System.out.println(v);
			map.forEach((key,va)->System.out.println(key+"->"+va));


			//remove方法:根据key和value都匹配时，才会真正删除
			map.remove(1,"vince");
			System.out.println(map.remove(1,"vince"));

			//replace(key,V,V)
			map.replace(1, "vince");        //直接替换成vince
			map.replace(1, "vince", "lala");//将vince替换成lala

			//compute方法：计算指定键的映射及其当前映射的值（如果没有当前映射， null ）
			map.compute(1,(k,va)->va+"2"); //这里compute传入的是BiFunction方法，该BiFunction方法需要传入两个参数，所以lambda表达式需要两个参数
			map.forEach((key,val)->System.out.println(key+"->"+val));
			/**
			 1->lala2
			 2->tom
			 3->haha
			 */

			//computeIfAbsent方法：如果key=4的值为空，才进行后面的计算
			map.computeIfAbsent(4,(k)->k+"3"); //这里computeIfAbsent传入的是Function方法，该Function方法需要传入一个参数，所以lambda表达式需要一个参数
			//这里：k+"3"  就是  apply(T t)的方法的具体实现，因为接口里没有实现apply方法，所以这里是自定义要实现apply的方法
			map.forEach((key,val)->System.out.println(key+"->"+val));
			/**
			 1->lala2
			 2->tom
			 3->haha
			 4->43
			 */

			//computeIfPresent方法：如果key=4的值不为空，才进行后面的计算
			map.computeIfPresent(4, (k,v3)->v3+"pp");
			map.forEach((key,val)->System.out.println(key+"->"+val));
			/**
			 1->lala2
			 2->tom
			 3->haha
			 4->43pp
			 */

			//merge方法：合并
			map.merge(8, "888",(oldVal,newVal)->oldVal.concat(newVal));
			map.forEach((key,val)->System.out.println(key+"->"+val));
			/**
			 key=4
			 1->lala2
			 2->tom
			 3->haha
			 4->43pp888
			 */
			/**
			 key=8时
			 1->lala2
			 2->tom
			 3->haha
			 4->43pp
			 8->888
			 */
	}

}
