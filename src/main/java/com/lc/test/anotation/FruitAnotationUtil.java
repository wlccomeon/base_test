package com.lc.test.anotation;

import java.lang.reflect.Field;

/**
 * 注解处理器
 */
public class FruitAnotationUtil {
    public static void getFruitInfo(Class<?> clazz) {

        String strFruitName = " 水果名称：";
        String strFruitColor = "  水果颜色：";
        String strFruitProvider = " 供应商信息：";

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(FruitName.class)) {
                strFruitName = strFruitName + field.getAnnotation(FruitName.class).value();
                System.out.println(strFruitName);
            } else if (field.isAnnotationPresent(FruitColor.class)) {
                strFruitColor = strFruitColor + field.getAnnotation(FruitColor.class).fruitColor().toString();
                System.out.println(strFruitColor);
            } else if (field.isAnnotationPresent(FruitProvider.class)) {
                FruitProvider fruitProvider = (FruitProvider) field.getAnnotation(FruitProvider.class);
                strFruitProvider = " 供应商编号：" + fruitProvider.id() + " 供应商名称：" + fruitProvider.name() + " 供应商地址：" + fruitProvider.address();
                System.out.println(strFruitProvider);
            }
        }

    }
}
