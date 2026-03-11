package com.lc.test.skills.retrytask;

import org.springframework.boot.convert.DurationStyle;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @desc        重试策略枚举
 * @author wlc
 * @date 2026/3/11 星期三
 */
public enum RetryStrategyEnum {
    /**
     * 固定重试策略：每次重试的等待时间固定不变
     * 例如：配置为"5s,10s,15s"，第 1 次重试等待 5 秒，第 2 次 10 秒，第 3 次及以后都是 15 秒
     * 数据库可以配置：1m,3m,5m,10m,30m,1h,3h,6h,12h,24h
     */
    FIXED("fixed", "固定") {
        @Override
        public Duration nextRetryDuration(String retryDuration, BigDecimal retryMultiple, int retryTime) {
            String[] retryDurations = retryDuration.split(",");
            //它表示两个时间点之间的时间量（时间间隔），用于替代旧的毫秒数计算。
            Duration duration;
            if (retryTime > retryDurations.length) {
                //// 解析字符串（Spring 的 DurationStyle）
                //Duration d4 = DurationStyle.detectAndParse("5s");    // 5 秒
                //Duration d5 = DurationStyle.detectAndParse("1m");    // 1 分钟
                //Duration d6 = DurationStyle.detectAndParse("1h");    // 1 小时
                duration = DurationStyle.detectAndParse(retryDurations[retryDurations.length - 1]);
            } else if (retryTime <= 0) {
                duration = DurationStyle.detectAndParse(retryDurations[0]);
            } else {
                duration = DurationStyle.detectAndParse(retryDurations[retryTime - 1]);
            }
            return duration;
        }
    },
    /**
     * 递增重试策略：每次重试的等待时间按倍数递增
     * 例如：初始 5 秒，倍数为 2，第 1 次重试 5 秒，第 2 次 10 秒，第 3 次 20 秒（5×2²）
     */
    INCREASE("increase", "递增") {
        @Override
        public Duration nextRetryDuration(String retryDuration, BigDecimal retryMultiple, int retryTime) {
            Duration duration = DurationStyle.detectAndParse(retryDuration);
            //举例:5×2²
            duration = duration.multipliedBy(retryMultiple.pow(retryTime).longValue());
            return duration;
        }

    };

    RetryStrategyEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }


    private String code;
    private String text;

    public String getCode() {
        return code;
    }


    public String getText() {
        return text;
    }

    public abstract Duration nextRetryDuration(String retryDuration, BigDecimal retryMultiple, int retryTime);

    public static Optional<RetryStrategyEnum> of(String code) {
        return Stream.of(values()).filter(e -> e.getCode().equals(code)).findAny();
    }


}
