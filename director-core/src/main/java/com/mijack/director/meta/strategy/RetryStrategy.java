package com.mijack.director.meta.strategy;

import lombok.Data;

/**
 * @author yuanyujie
 */
@Data
public class RetryStrategy {
    private int retryTime;

    public RetryStrategy() {
    }

    public RetryStrategy(int retryTime) {
        this.retryTime = retryTime;
    }

    public static RetryStrategy fixedTime(int i) {
        return new RetryStrategy(i);
    }

    public static RetryStrategy noRetry() {
        return fixedTime(0);
    }
}
