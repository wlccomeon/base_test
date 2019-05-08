package com.lc.test.enums;

public enum StatusEnum implements CommonEnum {
    WAITTING(0, "WAITTING", "等待"),
    STARTED(1, "STARTED", "开始"),
    END(2, "END", "结束");

    private int code;
    private String name;
    private String desc;

    StatusEnum(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
