package com.lc.test.io.img;

import lombok.Data;

import java.util.List;

/**
 * @author wlc
 * @description
 * @date 2021/3/1 0001 16:46
 */
@Data
public class SynthesisListVo {
    /**
     * 类型  image  text
     */
    private String type;

    /**
     * 文案字体 type为text时生效
     */
    private String textFontFamily;

    /**
     * 文案字体大小 type为text时生效
     */
    private Integer textFontSize;

    /**
     * 文案是否水平居中 true水平居中 type为text时生效
     */
    private Boolean textAlignCenter;

    /**
     * 文案颜色 type为2时生效
     */
    private List<Integer> textColor;

    /**
     * 水平偏移量 type为text & textAlignCenter为true时失效
     */
    private Integer offsetX;

    /**
     * 垂直偏移量
     */
    private Integer offsetY;

    /**
     * 图片地址 type为image时生效
     */
    private String imageUrl;

    /**
     * 文案 type为text时生效
     */
    private String text;

    /**
     * 宽度 type为image时生效
     */
    private Integer width;

    /**
     * 高度 type为image时生效
     */
    private Integer height;
}
