package com.lc.test.anotation;

import lombok.Data;

@Data
public class Apple {

    @FruitName(value = "Apple")
    private String appleName;

    @FruitColor(fruitColor = FruitColor.Color.GREEN)
    private String appleColor;

    @FruitProvider(id = 1,name = "红富士集团",address = "陕西省延安市哈哈哈哈")
    private String appleProvider;

}
