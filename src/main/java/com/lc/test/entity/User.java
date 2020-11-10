package com.lc.test.entity;

import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
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


//    public static void main(String[] args) {
//
////        a = "bbb"; //直接报错
//        num.getAndIncrement(); //这个就可以。。
//
//        System.out.println(21/100);
//        System.out.println(121/100);
//        try {
//            System.out.println("hahah");
//        }catch (Exception e){
//            System.out.println(1/0);
//        }
//    }

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

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }




}
