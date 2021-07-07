package com.lc.test;

import com.lc.test.transaction.TransactionalSample;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaseTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionTest {

    @Autowired
    private TransactionalSample transactionalSample;

    @Test
    public void testTxNormalOperateSimpleCase(){
        transactionalSample.testTxNormalOperateSimpleCase();
    }

    @Test
    public void testTxNotOperateSimpleCase(){
        transactionalSample.testTxNotOperateSimpleCase();
    }

}
