package com.lc.test.baseknowlege.collection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lc.test.entity.User;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * List集合测试
 * @author wlc
 */
public class ListTest {

    private static List<String> list = new ArrayList<>();
    static {
        list.add("add");
        list.add("delete");
        list.add("delete");
        list.add("update");
        list.add("query");
    }

	public static void main(String[] args) {
		Random random = new Random();
		for (int i = 0; i <10 ; i++) {
			System.out.println("Math.abs(random.nextInt())"+i+"="+Math.abs(random.nextInt()));
		}

		String a =  "1";

	}

}
