package com.lc.test.skills.callback;

import com.lc.test.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wlc
 * @desc
 * @date 2026/3/11 星期三
 */
@Slf4j
public class TaskService {

    public void insertTask(User user){
        log.info("开始插入用户信息。。。");
        log.info("用户信息：{}",user);
        log.info("插入用户信息完成。。。");
    }

}
