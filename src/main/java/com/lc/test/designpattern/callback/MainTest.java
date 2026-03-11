package com.lc.test.designpattern.callback;

import com.lc.test.entity.User;
import com.lc.test.utils.snowflakes.IdWorker;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author wlc
 * @desc
 * @date 2026/3/11 星期三
 */
@Slf4j
public class MainTest {


    public static void main(String[] args) {
        User user = User.builder().id(1).name("lc").sex(1).address("河北大名府").build();
        //这里的t->doCallBack(user)其实就是ITaskCallBack<User>的实现类，这里只是为了方便，实际开发中，这个类可以单独放在一个类中
        //submitTask(user, t -> doCallBack(t))
        //         │       │
        //         │       └─ ITaskCallBack<User> 的 callBack(User t)
        //         │          所以 t 的类型是 User
        //         │
        //         └─ T 被推断为 User，所以 data 也是 User 类型
        //总结：t 就是 submitTask 方法中 data 参数（也就是 user）通过回调接口传递给 Lambda 表达式的值。
        submitTask(user, t -> doCallBack(t));
    }

    public static<T> void submitTask(T data,ITaskCallBack<T> taskCallBack){
        log.info("开始执行任务前置处理=======");
        //其实是调用其实现类的重写方法
        taskCallBack.callBack(data);
        log.info("任务执行完成=======");
    }

    public static void doCallBack(User user){
        TaskService taskService = new TaskService();
        taskService.insertTask(user);
    }

}
