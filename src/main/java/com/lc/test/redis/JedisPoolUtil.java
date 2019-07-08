package com.lc.test.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Description: 使用单例模式产生jedispool
 * @author: wlc
 * @date: 2019/7/1 0001 23:16
 **/
public class JedisPoolUtil {

	private JedisPoolUtil(){};

	private volatile JedisPool jedisPool = null;

	public JedisPool getJedisPool(){

		if (jedisPool==null){
			synchronized (JedisPoolUtil.class){
				if (jedisPool==null){
					JedisPoolConfig config = new JedisPoolConfig();
					config.setMaxTotal(1000);
					config.setMaxWaitMillis(100*1000);
					config.setMaxIdle(20);
					//相当于获取jedis的时候进行一下ping
					config.setTestOnBorrow(true);
					jedisPool = new JedisPool(config,"192.168.20.139");
				}
			}
		}
		return jedisPool;
	}

	/**
	 * 释放jedis连接
	 * @param pool  所使用的连接池
	 * @param jedis jedis
	 */
	public void release(JedisPool pool, Jedis jedis){
		if (null!=jedis){
//			pool.
		}
	}
}
