package com.lc.test.controller;

import com.lc.test.entity.User;
import com.lc.test.entity.UserDTO;
import com.lc.test.utils.BeanCopierUtils;
import com.lc.test.utils.PoToDtoMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wlc
 * @description
 * @date 2020/11/18 0018 11:23
 */
@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {
    private static List<User> users = new ArrayList<>();
    static {
        for (int i = 0; i < 1000000; i++) {
            User user = new User();
            user.setId(i+1);
            user.setName("wlc"+i);
            user.setAddress("爪哇"+i);
            users.add(user);
        }
    }

    @GetMapping("/bean-copy")
    public void beanCopyTest(){

        //结论：从性能上来说：
        //      mapStruct >>  BeanCopier > Spring BeanUtils >> Apache BeanUtils
        //     从使用编辑性上来说，BeanCopier比较优雅

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<UserDTO> userDTOS1 = new ArrayList<>();
        for (User user : users) {
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
        for (User user : users) {
            userDTOS3.add(PoToDtoMapping.INSTANCE.copyUserToUserDTO(user));
        }
        stopWatch.stop();
        log.info("MapStruct 拷贝花费时间：{}ms",stopWatch.getLastTaskTimeMillis());

        stopWatch.start();
        List<UserDTO> userDTOS4 = new ArrayList<>();
        try{
            for (User user : users) {
                UserDTO userDTO = new UserDTO();
                org.apache.commons.beanutils.BeanUtils.copyProperties(userDTO,user);
                userDTOS4.add(userDTO);
            }
        }catch (Exception e){
            log.error("拷贝失败:{}",e);
        }
        stopWatch.stop();
        log.info("Apache BeanUtils花费时间：{}ms",stopWatch.getLastTaskTimeMillis());

        log.info("总共花费时间:{}ms",stopWatch.getTotalTimeMillis());

//        userDTOS.stream().forEach(System.out::println);
    }

    public void mapStruct(){

    }

}
