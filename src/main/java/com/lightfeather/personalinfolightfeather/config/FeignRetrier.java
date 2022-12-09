package com.lightfeather.personalinfolightfeather.config;

import feign.RetryableException;
import feign.Retryer;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@NoArgsConstructor
public class FeignRetrier implements Retryer {

    @Value("${feign_custom.retry.max:5}")
    private int retryMaxAttempt;

    @Value("${feign_custom.retry.interval:2000}")
    private long retryInterval;

    private int attempt = 1;


    public FeignRetrier(int retryMaxAttempt, Long retryInterval) {
        this.retryMaxAttempt = retryMaxAttempt;
        this.retryInterval = retryInterval;
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        log.info("Feign retry attempt {} due to {} ", attempt, e.getMessage());
        if (attempt++ == retryMaxAttempt) {
            throw e;
        }
        try {
            Thread.sleep(retryInterval);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Retryer clone() {
        return new FeignRetrier(retryMaxAttempt, retryInterval);
    }
}
