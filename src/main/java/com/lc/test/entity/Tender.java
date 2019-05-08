package com.lc.test.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by wlc on 2017/8/15 0015.
 */
@Data
public class Tender {
    private Integer id;
    private Integer uid;
    private String description;
    private Date createDate;
}
