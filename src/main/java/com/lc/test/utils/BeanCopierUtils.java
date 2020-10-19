package com.lc.test.utils;

import net.sf.cglib.beans.BeanCopier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * BeanCopier工具类
 * @author wlc
 *
 */

public class BeanCopierUtils {  
    Logger logger = LoggerFactory.getLogger(BeanCopierUtils.class);
	/**
	 * BeanCopier缓存，flyweight模式的运用，将BeanCopier缓存起来。
	 */
	public static Map<String, BeanCopier> beanCopierCacheMap = new HashMap<String, BeanCopier>();  
      
	/**
	 * 将source对象的属性拷贝到target对象中去
	 * @param source source对象
	 * @param target target对象
	 */
    public static void copyProperties(Object source, Object target){  
        String cacheKey = source.getClass().toString() + 
        		target.getClass().toString();  
        
        BeanCopier beanCopier = null;  
        
        if (!beanCopierCacheMap.containsKey(cacheKey)) {  
        	synchronized(BeanCopierUtils.class) {
        		 if (!beanCopierCacheMap.containsKey(cacheKey)) {  
        			 beanCopier = BeanCopier.create(source.getClass(), target.getClass(), false);  
        			 beanCopierCacheMap.put(cacheKey, beanCopier);  
        		 }
        	}
        } else {  
            beanCopier = beanCopierCacheMap.get(cacheKey);   
        }  
        
        beanCopier.copy(source, target, null);  
    }

	/**
	 * 使用方法：在目标类，比如vo、dto中添加该 克隆  方法
	 * @param clazz 目标Class对象
	 * @return 克隆后的对象
	 */
	public <T> T clone(Class<T> clazz) {
		T target = null;

		try {
			target = clazz.newInstance();
		} catch (Exception e) {
			logger.error("error", e);
		}

		BeanCopierUtils.copyProperties(this, target);
		return target;
	}
  
}  
