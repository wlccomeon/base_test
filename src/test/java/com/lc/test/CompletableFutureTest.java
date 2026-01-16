package com.lc.test;

import com.google.common.collect.Lists;
import com.lc.test.entity.User;
import com.lc.test.thread.callable.completablefuture.MyCompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @desc
 * @author wlc
 * @date 2026/1/15 星期四
 */
@EnableAsync
@Import(CompletableFutureTest.MyCompletableFuture.class)
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaseTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompletableFutureTest {


    //内部类想要被Spring管理需要声明为static，然后在测试类上显示的@Import该内部类
    @Autowired
    private MyCompletableFuture myCompletableFuture;
    @Test
    public void testCompletableFuture()  {
        int threadNum = 3;
        // 1. 创建任务时，建立“任务标识”到Future的映射，用LinkedHashMap保持顺序
        Map<String, CompletableFuture<User>> futureMap = new LinkedHashMap<>();
//        List<CompletableFuture<User>> futureList = Lists.newArrayListWithCapacity(threadNum);
        for (int i = 0; i < threadNum; i++) {
            //任务标记
            String taskName = "任务-wlc" + i;
            try {
                CompletableFuture<User> future = myCompletableFuture.checkUser(i);
                futureMap.put(taskName, future);;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        // 2. 使用线程安全的集合收集失败标识
        List<Map<String,String>> failedProcessList = new CopyOnWriteArrayList<>();
        // 3. 对每个Entry进行异常处理
        List<CompletableFuture<Optional<User>>> safelyHandledFutures = futureMap.entrySet()
                .stream().map(entry -> {
                    String taskName = entry.getKey();
                    CompletableFuture<User> future = entry.getValue();
                    // 在handle回调中，使用已绑定的taskName，而非尝试从future或空结果中获取(可能会造成空指针或者阻断异步线程导致同步等待)
                    return future.<Optional<User>>handle((user, throwable) -> {
                        if (throwable != null) {
                            log.error("任务[{}]执行失败，原因: {}", taskName, throwable.getMessage());
                            Map<String,String> failedProcess = new HashMap<>();
                            failedProcess.put(taskName,throwable.getMessage());
                            failedProcessList.add(failedProcess);
                            return Optional.empty();
                        }
                        return Optional.ofNullable(user);
                    });
                }).collect(Collectors.toList());
        // 2. 等待所有被“包装”后的Future完成
        CompletableFuture<Void> allHandled = CompletableFuture.allOf(
                safelyHandledFutures.toArray(new CompletableFuture[0])
        );
        allHandled.join();
        // 3. 收集结果
        List<User> successfulResults = safelyHandledFutures.stream()
                .map(CompletableFuture::join)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        log.info("成功完成任务数：{}，失败任务数：{}",successfulResults.size(),failedProcessList.size());
        System.out.println("successfulResults = " + successfulResults);
        System.out.println("failedProcessList = " + failedProcessList);
    }

    @Component
    static class MyCompletableFuture {
        //@EnableAsync需要加载测试类上
        @Async
        public CompletableFuture<User> checkUser(Integer id) throws InterruptedException {
            User user = new User();
            user.setName("wlc"+id);
            log.info("开始执行任务：{}",user.getName());
            TimeUnit.SECONDS.sleep(1);
            if (id==1){
                //抛一个异常
                user.getMobile().substring(1);
            }
            return CompletableFuture.completedFuture(user);
        }
    }

    static class User {
        private String name;
        private String mobile;
        public String getMobile() {
            return mobile;
        }
        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }




}
