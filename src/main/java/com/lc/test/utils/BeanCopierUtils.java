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

    private static Logger logger = LoggerFactory.getLogger(BeanCopierUtils.class);
	/**
	 * BeanCopier缓存，flyweight模式的运用，将BeanCopier缓存起来。
	 */
	private static Map<String, BeanCopier> beanCopierCacheMap = new HashMap<String, BeanCopier>();
      
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
        		 }else{
					 beanCopier = beanCopierCacheMap.get(cacheKey);
				 }
        	}
        } else {  
            beanCopier = beanCopierCacheMap.get(cacheKey);   
        }  
        
        beanCopier.copy(source, target, null);
    }



	/**
	 * 更优雅的属性复制方法
	 * @param source 数据源对象
	 * @param clazz 目标Class
	 * @return 克隆后的对象
	 */
	public static <T> T copyProperties(Object source,Class<T> clazz) {
		T target = null;
		try {
			target = clazz.newInstance();
			BeanCopierUtils.copyProperties(source, target);
		} catch (Exception e) {
			logger.error("error", e);
		}
		return target;
	}
  
}  
