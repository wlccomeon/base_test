package com.lc.test.baseknowlege;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author wlc
 * @desc: LogTest
 * @datetime: 2024/9/24 0024 16:42
 */
@Slf4j
public class LogTest {



    @Test
    public void testPrintException(){

        try {
            int a = 1/0;
        }catch (Exception e){
            log.error("出错了",e);
            log.info("啊哈哈哈",e.getMessage());
        }


    }






}

