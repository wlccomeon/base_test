package com.lc.test.baseknowlege.enums;

/**
 * 枚举接口
 */
public interface CommonEnum {
    //此处对应枚举的字段,如状态枚举StatusEnum定义了code,name,desc
    //那么这里定义这个三个字段的get方法,可以获取到所有的字段
    int getCode();

    String getName();

    String getDesc();

}