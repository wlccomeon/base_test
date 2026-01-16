package com.lc.test.utils;

import com.lc.test.entity.User;
import com.lc.test.entity.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wlc
 * @description
 * @date 2020/11/18 0018 11:09
 */
@Slf4j
public class UtilTest {
    @Test
    public void testBeanCopy(){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            User user = new User();
            user.setId(i+1);
            user.setName("wlc"+i);
            user.setAddress("爪哇"+i);
            users.add(user);
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<UserDTO> userDTOS1 = new ArrayList<>();
        for (User user : users) {
//			UserDTO userDTO = new UserDTO();
//            BeanCopierUtils.copyProperties(user, userDTO);
//            userDTOS.add(userDTO);
            userDTOS1.add(BeanCopierUtils.copyProperties(user,UserDTO.class));
        }
        stopWatch.stop();
        log.info("BeanCopier花费时间：{}ms",stopWatch.getLastTaskTimeMillis());
        stopWatch.start();
        List<UserDTO> userDTOS2 = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user,userDTO);
            userDTOS2.add(userDTO);
        }
        stopWatch.stop();
        log.info("Spring BeanUtils花费时间：{}ms",stopWatch.getLastTaskTimeMillis());

        stopWatch.start();
        List<UserDTO> userDTOS3 = new ArrayList<>();
        try{
            for (User user : users) {
                UserDTO userDTO = new UserDTO();
                org.apache.commons.beanutils.BeanUtils.copyProperties(userDTO,user);
                userDTOS3.add(userDTO);
            }
        }catch (Exception e){
            log.error("拷贝失败:{}",e);
        }
        stopWatch.stop();
        log.info("Apache BeanUtils花费时间：{}ms",stopWatch.getLastTaskTimeMillis());

        log.info("总共花费时间:{}ms",stopWatch.getTotalTimeMillis());

//        userDTOS.stream().forEach(System.out::println);

    }


    @Test
    public void testStopWatch() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("第一次");
        TimeUnit.SECONDS.sleep(2);
        stopWatch.stop();
        System.out.println("stopWatch.getLastTaskTimeMillis() 1 = " + stopWatch.getLastTaskTimeMillis());
        stopWatch.start("第2次");
        TimeUnit.SECONDS.sleep(1);
        stopWatch.stop();
        System.out.println("stopWatch.getLastTaskTimeMillis() 2 = " + stopWatch.getLastTaskTimeMillis());
        System.out.println("stopWatch.prettyPrint() = " + stopWatch.prettyPrint());

    }

}
