package com.lc.test.redis;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: jedis api测试，在主从复制的条件下进行的测试。
 * @author: wlc
 * @date: 2019/7/1 0001 22:10
 **/
public class ApiTest {


	public static void main(String[] args) {

		String host = "192.168.20.139";
		//6379为只读,6380为写
		int port = 6380;
		int port_r = 6379;
		Jedis jedis = new Jedis(host,port);

		String v1 = jedis.get("k1");
		System.out.println(v1);

		String result = jedis.set("k5", "v5");
		System.out.println(result);

		System.out.println(jedis.ping("ping result"));

		String msetResult = jedis.mset("m1", "v1", "m2", "v2");
		System.out.println("msetResult-->>"+msetResult);

		Map<String,String> hsetMap = new HashMap<>(4);
		hsetMap.put("age","20");
		hsetMap.put("name","lc");
		hsetMap.put("height","170cm");
		Long hsetResult = jedis.hset("jedis", hsetMap);
		System.out.println("hsetResult-=>"+hsetResult);
		Map<String, String> hset = jedis.hgetAll("jedis");
		System.out.println("hset-->>"+hset.toString());
	}


}
