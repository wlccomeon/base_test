package com.lc.test.utils.zip;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum PackageTypeEnum {
    ZIP("application/zip", ".zip"),
    RAR("application/octet-stream", ".rar");
    public String type;
    public String fileSuffix;

    public static String getFileSuffix(String type) {
        for (PackageTypeEnum orderTypeEnum : PackageTypeEnum.values()) {
            if (orderTypeEnum.type.equals(type)) {
                return orderTypeEnum.fileSuffix;
            }
        }
        return null;
    }
}
