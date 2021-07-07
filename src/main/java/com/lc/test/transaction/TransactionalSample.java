package com.lc.test.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 测试类
 * @author wlc
 */
@Component
public class TransactionalSample {


    @Autowired
    private SimpleDemo simpleService;

    public void testTxNormalOperateSimpleCase() {
        System.out.println("============ 事务正常工作 start ========== ");
        simpleService.query("transaction before", 130);
        try {
            // 事务可以正常工作
            simpleService.testRuntimeExceptionTrans(130);
        } catch (Exception e) {
        }
        simpleService.query("transaction end", 130);
        System.out.println("============ 事务正常工作 end ========== \n");

        //结果:
        //============ 事务正常工作 start ==========
        //transaction before >>>> {id=130, name=初始化, money=200, is_deleted=false, create_at=2020-01-19 16:15:21.0, update_at=2020-01-19 16:15:21.0}
        //after updateMoney name >>>> {id=130, name=更新, money=200, is_deleted=false, create_at=2020-01-19 16:15:21.0, update_at=2020-01-19 16:15:22.0}
        //transaction end >>>> {id=130, name=初始化, money=200, is_deleted=false, create_at=2020-01-19 16:15:21.0, update_at=2020-01-19 16:15:21.0}
        //============ 事务正常工作 end ==========
    }

    /**
     * 事务不生效的case
     */
    public void testTxNotOperateSimpleCase() {
        System.out.println("============ 事务不生效 start ========== ");
        simpleService.query("transaction before", 140);
        try {
            // 因为抛出的是非运行异常，不会回滚
            simpleService.testNormalException(140);
        } catch (Exception e) {
        }
        simpleService.query("transaction end", 140);
        System.out.println("============ 事务不生效 end ========== \n");


        System.out.println("============ 事务生效 start ========== ");
        simpleService.query("transaction before", 150);
        try {
            // 注解中，指定所有异常都回滚
            simpleService.testSpecialException(150);
        } catch (Exception e) {
        }
        simpleService.query("transaction end", 150);
        System.out.println("============ 事务生效 end ========== \n");

        //结果:
        //============ 事务不生效 start ==========
        //transaction before >>>> {id=140, name=初始化, money=200, is_deleted=false, create_at=2020-01-19 16:15:21.0, update_at=2020-01-19 16:15:21.0}
        //after updateMoney name >>>> {id=140, name=更新, money=200, is_deleted=false, create_at=2020-01-19 16:15:21.0, update_at=2020-01-19 16:15:22.0}
        //transaction end >>>> {id=140, name=更新, money=210, is_deleted=false, create_at=2020-01-19 16:15:21.0, update_at=2020-01-19 16:15:22.0}
        //============ 事务不生效 end ==========
        //
        //============ 事务生效 start ==========
        //transaction before >>>> {id=150, name=初始化, money=200, is_deleted=false, create_at=2020-01-19 16:15:21.0, update_at=2020-01-19 16:15:21.0}
        //after updateMoney name >>>> {id=150, name=更新, money=200, is_deleted=false, create_at=2020-01-19 16:15:21.0, update_at=2020-01-19 16:15:22.0}
        //transaction end >>>> {id=150, name=初始化, money=200, is_deleted=false, create_at=2020-01-19 16:15:21.0, update_at=2020-01-19 16:15:21.0}
        //============ 事务生效 end ==========
    }
}
