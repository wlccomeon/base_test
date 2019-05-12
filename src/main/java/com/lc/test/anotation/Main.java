package com.lc.test.anotation;

import com.lc.test.anotation.fruit.Apple;
import com.lc.test.anotation.fruit.FruitAnotationUtil;

public class Main {
    public static void main(String[] args) {
        FruitAnotationUtil.getFruitInfo(Apple.class);
    }
}
