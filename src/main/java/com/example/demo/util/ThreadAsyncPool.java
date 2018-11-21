package com.example.demo.util;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

//开启对异步任务的支持

/**
 * @author vitem
 */
@Component
@EnableAsync
public class ThreadAsyncPool implements AsyncConfigurer {

    ThreadPoolExecutor  executorPool = null;

    int ThreadPoolSize = 20;//初始线程池连接数

    @Override
    public ThreadPoolExecutor getAsyncExecutor() {
        if(null == executorPool){
            initThreadPool();
        }
        return executorPool;
    }

    public void initThreadPool() {
        //创建一个可固定数量线程的线程池和一个无限队列
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(ThreadPoolSize);
        executorPool = threadPool;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }

}