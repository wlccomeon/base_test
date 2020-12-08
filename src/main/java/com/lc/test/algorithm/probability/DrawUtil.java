package com.lc.test.algorithm.probability;

import lombok.Data;

import java.util.*;

/**
 * /**
 *  * 抽奖工具类
 *
 *  * 整体思想：
 *  * 奖品集合 + 概率比例集合
 *  * 将奖品按集合中顺序概率计算成所占比例区间，放入比例集合。并产生一个随机数加入其中，排序。
 *
 *  * 排序后，随机数落在哪个区间，就表示那个区间的奖品被抽中。
 *
 *  * 返回的随机数在集合中的索引，该索引就是奖品集合中的索引。
 *
 *  * 比例区间的计算通过概率相加获得。
 *  * @author irving
 *  * @since 2017年7月23日 下午9:48:23
 *  * @version MARK 0.0.1
 *  */
public class DrawUtil {

    public static Gift draw(List<Gift> gifts) {
        if (null == gifts || gifts.size() == 0) {
            return null;
        }
        //自小到大排序
        gifts.sort(Comparator.comparingDouble(Gift::getProb));
//        gifts.sort((o1, o2) -> (o1.prob - o2.prob) > 0 ? 1 : -1);
        gifts.forEach(System.out::println);

        List<Double> probLists = new ArrayList<>(gifts.size());
        Double sumProb = 0D;
        for (Gift gift : gifts) {
            sumProb += gift.getProb();
        }
        if (sumProb <= 0) {
            return null;
        }

        // 归一化概率端点
        Double rate = 0D;
        for (Gift gift : gifts) {
            rate += gift.getProb();
            probLists.add(rate / sumProb);
        }

        double random = Math.random();
        probLists.add(random);
        Collections.sort(probLists);

        probLists.forEach(System.out::println);

        int index = probLists.indexOf(random);
        if (index >= 0) {
            return gifts.get(index);
        }
        return null;
    }

    public static void main(String[] args) {
        List<Gift> gifts = new ArrayList<>();
        Gift nothing = new Gift("谢谢惠顾", 0.5D);
        Gift vip = new Gift("XX会员1个月", 0.4D);
        Gift phone = new Gift("手机", 0.1D);

        gifts.add(nothing);
        gifts.add(phone);
        gifts.add(vip);

        // 抽奖
//  Gift g = draw(gifts);

        // 以下是测试统计
        Map<String,Integer> countMap = new HashMap<>();
        for (Gift gift: gifts) {
            countMap.put(gift.getName(), 0);
        }
        countMap.put("null", 0);
        for (int i=0; i<100000; i++) {
            // 抽一个
            Gift gift = draw(gifts);
            String name = "null";
            if (null != gift) {
                name = gift.getName();
            }

            int count = countMap.get(name);
            countMap.put(name, ++count);
        }

        for (Map.Entry entry : countMap.entrySet()) {
            System.out.println("抽到"+entry.getKey()+", "+entry.getValue()+"次");

        }
    }
}

/**
 * 奖品类
 */
@Data
class Gift {
    String name;
    Double prob;

    public Gift(String name, Double prob) {
        this.name = name;
        this.prob = prob;
    }
}
