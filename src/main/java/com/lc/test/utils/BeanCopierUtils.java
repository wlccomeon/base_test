package com.lc.test.utils;

import com.lc.test.entity.User;
import com.lc.test.entity.UserDTO;
import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BeanCopier工具类
 * @author wlc
 *
 */

public class BeanCopierUtils {

    private static Logger logger = LoggerFactory.getLogger(BeanCopierUtils.class);
	/**dd
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
        		 }
        	}
        } else {  
            beanCopier = beanCopierCacheMap.get(cacheKey);   
        }  
        
        beanCopier.copy(source, target, null);
    }

    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            User user = new User();
            user.setId(i+1);
            user.setName("wlc"+i);
            user.setAddress("爪哇"+i);
            users.add(user);
        }
        List<UserDTO> userDTOS1 = new ArrayList<>();
        for (User user : users) {
//			UserDTO userDTO = new UserDTO();
//            BeanCopierUtils.copyProperties(user, userDTO);
//            userDTOS.add(userDTO);
			userDTOS1.add(BeanCopierUtils.clone(user,UserDTO.class));
        }
		List<UserDTO> userDTOS2 = new ArrayList<>();
        try{
			for (User user : users) {
				UserDTO userDTO = new UserDTO();
				BeanUtils.copyProperties(userDTO,user);
			}
		}catch (Exception e){

		}

//        userDTOS.stream().forEach(System.out::println);


	}



	/**
	 * 使用方法：在目标类，比如vo、dto中添加该 克隆  方法
	 * @param clazz 目标Class对象
	 * @return 克隆后的对象
	 */
	public static <T> T clone(Object source,Class<T> clazz) {
		T target = null;

		try {
			target = clazz.newInstance();
		} catch (Exception e) {
			logger.error("error", e);
		}

		BeanCopierUtils.copyProperties(source, target);
		return target;
	}
  
}  
