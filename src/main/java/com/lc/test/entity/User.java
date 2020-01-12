package com.lc.test.entity;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by wlc on 2017/8/15 0015.
 */
public class User implements Serializable {

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", address='" + address + '\'' +
                '}';
    }
}
