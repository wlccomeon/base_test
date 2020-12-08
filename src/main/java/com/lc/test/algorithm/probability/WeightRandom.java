package com.lc.test.algorithm.probability;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author wlc
 * @description
 * @date 2020/11/26 0026 18:05
 */
public class WeightRandom {
    static List<WeightCategory> categorys = new ArrayList<WeightCategory>();
    private static Random random = new Random();

    public static int a =0;
    public static int b = 0;
    public static int c = 0;
    public static int d =0;

    public static void initData() {
        WeightCategory wc1 = new WeightCategory(1, "一等奖", 1);
        WeightCategory wc2 = new WeightCategory(2, "二等奖", 10);
        WeightCategory wc3 = new WeightCategory(3, "三等奖", 20);
        WeightCategory wc4 = new WeightCategory(4, "四等奖", 30);
        categorys.add(wc1);
        categorys.add(wc2);
        categorys.add(wc3);
        categorys.add(wc4);
    }

    public static void main(String[] args) {
        // 循环100次 看下权重效果
        for (int i = 0; i < 5; i++) {
            mainWeightRandom();
        }

        System.out.println("a被抽：" + a);
        System.out.println("b被抽：" + b);
        System.out.println("c被抽：" + c);
        System.out.println("d被抽：" + d);
    }

    // 权重主方法
    public static void mainWeightRandom() {
        initData();
        Integer weightSum = 0;
        for (WeightCategory wc : categorys) {
            weightSum += wc.getWeight();
        }

        if (weightSum <= 0) {
            System.err.println("Error: weightSum=" + weightSum.toString());
            return;
        }
        Integer n = random.nextInt(100); // n in [0, weightSum)
        Integer m = 0;
        for (WeightCategory wc : categorys) {
            if (m <= n && n < m + wc.getWeight()) {
                System.out.println("你抽中了： " + wc.getCategory());
                if (wc.getNum() == 1) {
                    a+=1;
                }
                if (wc.getNum() == 2) {
                    b+=1;
                }
                if (wc.getNum() == 3) {
                    c+=1;
                }
                if (wc.getNum() == 4) {
                    d+=1;
                }
                break;
            }
            m += wc.getWeight();
        }
    }

}

class WeightCategory {
    private Integer num; // 奖品编号
    private String category; // 奖项名称
    private Integer weight; // 权重（抽中概率）

    public WeightCategory() {
        super();
    }

    public WeightCategory(Integer num, String category, Integer weight) {
        super();
        this.setNum(num);
        this.setCategory(category);
        this.setWeight(weight);
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
