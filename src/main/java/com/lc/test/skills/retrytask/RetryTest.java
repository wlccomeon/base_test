package com.lc.test.skills.retrytask;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;

/**
 * @author wlc
 * @desc
 * @date 2026/3/11 星期三
 */
public class RetryTest {

    public static void main(String[] args) {
        // ========== 示例 1：固定重试策略 ==========
        testFixedStrategy();

        // ========== 示例 2：递增重试策略 ==========
        testIncreaseStrategy();

        // ========== 示例 3：通过 code 获取枚举 ==========
        testGetByCode();
    }

    /**
     * 在任务执行失败时的重试逻辑
     * 实际使用过程中：1.可以设置成一个定时任务的形式 2.可以结合MQ使用消息消费的形式
     */
    public void executeWithRetry(Runnable task, RetryStrategyEnum strategy, String retryDuration) {
        int maxRetries = 5;
        BigDecimal retryMultiple = new BigDecimal("2");
        int currentRetry = 0;

        while (currentRetry < maxRetries) {
            try {
                task.run();
                return; // 成功则返回
            } catch (Exception e) {
                currentRetry++;

                // 计算下次重试等待时间
                Duration waitTime = strategy.nextRetryDuration(
                        retryDuration,
                        retryMultiple,
                        currentRetry
                );

                System.out.println("任务失败，" + waitTime.getSeconds() + "秒后重试...");

                // 等待指定时间
                try {
                    Thread.sleep(waitTime.toMillis());
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    /**
     * 测试固定重试策略
     */
    private static void testFixedStrategy() {
        System.out.println("=== 固定重试策略 ===");

        // 配置：第 1 次 1 分钟，第 2 次 3 分钟，第 3 次 5 分钟，第 4 次 10 分钟，之后都是 30 分钟
        String retryDuration = "1m,3m,5m,10m,30m";
        BigDecimal retryMultiple = BigDecimal.ONE; // 固定策略不需要倍数

        for (int i = 1; i <= 7; i++) {
            Duration duration = RetryStrategyEnum.FIXED.nextRetryDuration(
                    retryDuration,
                    retryMultiple,
                    i
            );
            System.out.println("第 " + i + " 次重试，等待：" + duration.getSeconds() + " 秒");
        }
        // 输出：
        // 第 1 次重试，等待：60 秒
        // 第 2 次重试，等待：180 秒
        // 第 3 次重试，等待：300 秒
        // 第 4 次重试，等待：600 秒
        // 第 5 次重试，等待：1800 秒
        // 第 6 次重试，等待：1800 秒（保持最大值）
        // 第 7 次重试，等待：1800 秒
    }

    /**
     * 测试递增重试策略
     */
    private static void testIncreaseStrategy() {
        System.out.println("\n=== 递增重试策略 ===");

        // 初始 5 秒，每次乘以 2 倍
        String retryDuration = "5s";
        BigDecimal retryMultiple = new BigDecimal("2");

        for (int i = 1; i <= 5; i++) {
            Duration duration = RetryStrategyEnum.INCREASE.nextRetryDuration(
                    retryDuration,
                    retryMultiple,
                    i
            );
            System.out.println("第 " + i + " 次重试，等待：" + duration.getSeconds() + " 秒");
        }
        // 输出：
        // 第 1 次重试，等待：10 秒 (5×2^1)
        // 第 2 次重试，等待：20 秒 (5×2^2)
        // 第 3 次重试，等待：40 秒 (5×2^3)
        // 第 4 次重试，等待：80 秒 (5×2^4)
        // 第 5 次重试，等待：160 秒 (5×2^5)
    }

    /**
     * 测试通过 code 获取枚举
     */
    private static void testGetByCode() {
        System.out.println("\n=== 通过 code 获取枚举 ===");

        Optional<RetryStrategyEnum> fixed = RetryStrategyEnum.of("fixed");
        fixed.ifPresent(e ->
                System.out.println("策略：" + e.getText() + ", code=" + e.getCode())
        );

        Optional<RetryStrategyEnum> increase = RetryStrategyEnum.of("increase");
        increase.ifPresent(e ->
                System.out.println("策略：" + e.getText() + ", code=" + e.getCode())
        );

        Optional<RetryStrategyEnum> unknown = RetryStrategyEnum.of("unknown");
        System.out.println("未知策略是否存在：" + unknown.isPresent());
    }


}
