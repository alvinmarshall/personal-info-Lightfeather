package com.lightfeather.personalinfolightfeather.runner;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class BackgroundTaskService {
    private final ExecutorService executorService;

    public BackgroundTaskService(@Qualifier("fixedThreadPool") ExecutorService executorService) {
        this.executorService = executorService;
    }

    public <T> Future<T> execute(Callable<T> callable) {
        return executorService.submit(callable);
    }
}
