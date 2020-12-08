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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wlc
 * @description
 * @date 2020/11/18 0018 11:23
 */
@Slf4j
@Controller
@RequestMapping("/test")
public class BeanCopyTestController {

    static {

    }

    @GetMapping("/bean-copy")
    public void beanCopyTest(){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            User user = new User();
            user.setId(i+1);
            user.setName("wlc"+i);
            user.setAddress("爪哇"+i);
            users.add(user);
        }
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
    }

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("##0.00");
        double a = 88;
        double b = 98;
        double c = a/b;
        System.out.println("c = " + c);
        System.out.println("df.format(c) = " + df.format(c));

        double[] doubles = {88,89,80,55};
        double sum = Arrays.stream(doubles).sum();
        double aDouble = doubles[1];
        double result = aDouble / sum;

        System.out.println("result = " + result);
        System.out.println("df.format(result) = " + df.format(result));

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> subList = integers.subList(0, 4);
        subList.forEach(System.out::println);

        System.out.println("35%10 = " + 35 % 10);
        System.out.println("35/10 = " + 35 / 10);

    }

}
