package com.lc.test.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wlc
 * @description
 * @date 2020/12/11 0011 10:38
 */
@Data
public class AllTypeEntity {
    private Integer id;
    private Double weight;
    private Float height;
    private Long createTime = System.currentTimeMillis();
    private Short age;
    private Byte status;
    private Boolean available;
    private String name;
    private String[] array = {"1","2","3"};
    private List<String> props = new ArrayList<>();
    private Map<String,Object> exts = new HashMap<>();
}
