package com.lc.test.algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @desc 一个实现最简单的LRU方法
 * @author wlc
 * @date 2019-12-27 14:26:14
 */
public class LRUCache<K,V> extends LinkedHashMap<K,V> {
	/**缓存大小*/
	private final int CACHE_SIZE;

	public LRUCache(int cacheSize){
		//这块就是设置一个hashmap的初始化大小，最后一个true指的是linkedhashMap是按照访问顺序来进行排序，最近访问的放在头，最老访问的放在尾
		super((int)Math.ceil(cacheSize/0.75)+1,0.75f,true);
		this.CACHE_SIZE=cacheSize;
	}
	@Override
	protected boolean removeEldestEntry(Map.Entry eldest){
		//当map中的数据量大于指定缓存个数的时候，就自动删除最老的数据
		return size()>CACHE_SIZE;
	}
}
