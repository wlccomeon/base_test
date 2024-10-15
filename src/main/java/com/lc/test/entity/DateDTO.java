package com.lc.test.entity;

/**
 * @author wlc
 * @desc: Event
 * @datetime: 2024/9/26 0026 16:56
 */
import java.util.Date;

public class DateDTO {
    private String name;
    private Date eventDate;

    // 构造函数
    public DateDTO(String name, Date eventDate) {
        this.name = name;
        this.eventDate = eventDate;
    }

    // getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
}

