package com.lc.test.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wlc
 * @description
 * @date 2020-10-2020/10/21 0021 15:21
 */
public class IegSerialNumUtil {

    private static Logger logger = LoggerFactory.getLogger(IegSerialNumUtil.class);

    private static final Lock lock = new ReentrantLock();
    public static final int LOCK_SECONDS = 1;

    /**
     * 抓米交易序列号生成
     * @param businessCode 业务code
     * @param uid
     * @return
     * @throws Exception
     */
    public static String generator(String businessCode,Long uid) throws Exception {
        boolean isLock = false;
        logger.info("抓米业务流水号生成参数 businessCode:{}或uid:{}",businessCode,uid);
        try {
            isLock = lock.tryLock(LOCK_SECONDS, TimeUnit.SECONDS);
            if (!isLock) {
                throw new RuntimeException("获取锁失败");
            }
            StringBuilder sb = new StringBuilder();
            if(StringUtils.isEmpty(businessCode) || Objects.isNull(uid)){
                throw new RuntimeException("流水号必要参数为空!");
            }
            sb.append(businessCode).append(String.format("%08d",uid))
                    .append(System.currentTimeMillis());
            return sb.toString();
        }catch (Exception e){
            logger.error("抓米业务流水号生成失败 :{}", e.getMessage());
            throw new RuntimeException("出错了");
        }finally {
            if (isLock) {
                logger.info("抓米业务流水号解锁..");
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws Exception {
//        List<String> orderList = new ArrayList<String>();
//        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++) {
//            String orderId = IegSerialNumUtil.generator("DG",100038L);
//            if (orderList.contains(orderId)) {
//                logger.info("orderId:{}", orderId);
//                logger.info("orderList:{},list:{}", new Object[] { orderList.size(), orderList });
//                logger.error("序列号重复！");
//            }
//            orderList.add(orderId);
//        }
//        logger.info("generateOrder cost:{}", System.currentTimeMillis() - startTime);
        System.out.println("System.currentTimeMillis() = " + System.currentTimeMillis());
        System.out.println("System.nanoTime() = " + System.nanoTime());
    }

}
