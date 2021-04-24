package com.lc.test.baseknowlege.date;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author wlc
 * @description
 * @date 2021/3/16 0016 11:36
 */
@Service
public class ScheduleTest {

    @Scheduled(cron = "0 0 0 */1 * ?")
    public void scheduleTest(){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        System.out.println("dateFormat.format(new Date(System.currentTimeMillis())) = " + dateFormat.format(new Date(System.currentTimeMillis())));
    }

    @Test
    public void test(){
        System.out.println("JSON.toJSONString(null) = " + JSON.toJSONString(null));
        boolean b = null != null;
        System.out.println("b = " + b);
    }

}
