package com.lc.test.baseknowlege;

import com.alibaba.fastjson.JSON;
import com.lc.test.entity.Tender;
import com.lc.test.entity.User;
import lombok.extern.java.Log;

/**
 * Created by wlc on 2017/8/15 0015.
 */
@Log
public class NpeTest {
    public static void main(String[] args) {
        testGetNPE();
    }
    public static void testGetNPE(){
        User user = new User();
        user.setId(1);
        user.setName("wlc");
        Tender tender = new Tender();
        tender.setUid(user.getId());
        tender.setDescription(user.getName());
        System.out.println(JSON.toJSONString(tender));
        log.info("结果"+JSON.toJSONString(tender));
    }
}
