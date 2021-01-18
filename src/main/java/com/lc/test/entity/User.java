package com.lc.test.entity;

import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wlc on 2017/8/15 0015.
 * @author wlc c
 */
@Data
public class User implements Serializable,Cloneable {
    private static final AtomicLong num = new AtomicLong(0);
    private static final String a = "a";

    public User(Integer id,String name,Integer sex,String address){
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.address = address;
    }
    public User(){}
    private Integer id;
    private String name;
    private Integer sex;
    private String address;
    private List<User> families;
    private Map<String,Object> params;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name);
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        User user = new User();
        user.setName("lc");
        user.setAddress("河北大名府");
        Method[] methods = User.class.getDeclaredMethods();
        for (Method method : methods) {
            if(method.getName().equals("getName")){
                Object fieldValue = method.invoke(user, null);
                //结果：lc
                System.out.println("fieldValue = " + fieldValue.toString());
                System.out.println("captureName(fieldValue.toString()) = " + captureName(fieldValue.toString()));
                System.out.println("captureName(\"Lc\") = " + captureName("Lc"));
            }else if(method.getName().equals("getId")){
                Object filedValue = method.invoke(user, null);
                System.out.println("filedValue id = " + filedValue);
            }
        }
    }

    /**
     * 将字符串的首字母转大写
     * @param str 需要转换的字符串
     * @return
     */
    private static String captureName(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        // 偏移之前需要判断是否为小写字母
        char[] cs=str.toCharArray();
        char c = cs[0];
        if (c >= 'a' && c<= 'z'){
            cs[0] = (char)(c-32);
        }
        return String.valueOf(cs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public static AtomicLong getNum() {
        return num;
    }

    public static String getA() {
        return a;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<User> getFamilies() {
        return families;
    }

    public void setFamilies(List<User> families) {
        this.families = families;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
