//package com.lc.test.utils;
//
//import lombok.Value;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * @author wlc
// * @description
// * @date 2020-10-2020/10/21 0021 11:15
// */
//@Component
//public class SerialNumberGenerator {
//
//    private static Logger log = LoggerFactory.getLogger(SerialNumberGenerator.class);
//
//    //系统id，取配置文件中配置参数.多个节点可配置不同参数
//    @Value("${credit.system.key}")
//    private String systemKey;
//
//    //原子操作类
//    private static AtomicInteger count = new AtomicInteger(0);
//
//    public String getSerialNumber() throws Exception {
//        if(StringUtils.isBlank(systemKey)){
//            log.error("SerialNumberGenerator getSerialNumber systemKey is null ");
//            throw new Exception("error");
//        }
//        if(count.get() == 10000){
//            count.set(0);
//        }
//        //当前日期
//        String currentTime = DateUtils.getDateByFormate(new Date(),DateUtils.DATE_FORMAT);
//        //当前值,并补位0
//        String currentIndex =String.format("%05d", count.get());
//        String serialNumber = systemKey+currentTime+currentIndex;
//
//        log.info("SerialNumberGenerator getSerialNumber is:"+ serialNumber );
//        //++
//        count.incrementAndGet();
//        return serialNumber;
//    }
//}
//
