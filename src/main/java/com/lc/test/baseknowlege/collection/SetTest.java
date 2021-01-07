package com.lc.test.baseknowlege.collection;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wlc
 * @description
 * @date 2020/12/21 0021 20:03
 */
public class SetTest {

    public static void main(String[] args) {
        Set<Rank> set = new HashSet<>();
        for (int i = 0; i < 2; i++) {
            Rank rank = new Rank();
            rank.setRankNo(1);
            rank.setRoomId(String.valueOf(1));
            set.add(rank);
        }
        System.out.println("set = " + set.toString());
    }

    @Data
    @EqualsAndHashCode
    static class Rank{
        private String roomId;
        private Integer rankNo;
    }

}
