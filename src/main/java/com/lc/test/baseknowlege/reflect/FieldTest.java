package com.lc.test.baseknowlege.reflect;

import com.lc.test.entity.User;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @author wlc
 * @description
 * @date 2020/12/14 0014 11:29
 */
public class FieldTest {


    /**
     * 字段的泛型类型获取
     * @throws Exception
     */
    @Test
    public void genericTypeTest() throws Exception{
        User user = new User();
        Field field = User.class.getDeclaredField("families");
        Type genericType = field.getGenericType();
        Class<?> type = field.getType();
        //带泛型及参数类型：genericType = java.util.List<com.lc.test.entity.User>
        //type = interface java.util.List
        System.out.println("genericType = " + genericType);
        System.out.println("type = " + type);

        //泛型类型参数：class com.lc.test.entity.User
        if(genericType instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            Arrays.stream(actualTypeArguments).forEach(System.out::println);
        }

        //获取setName方法，为user实例中的name属性赋值
        //user = User(id=null, name=哈哈哈哈, sex=null, address=null, families=null, params=null)
        Method method = User.class.getMethod("setName",String.class);
        method.invoke(user,"哈哈哈哈");
        System.out.println("user = " + user.toString());

    }
}
