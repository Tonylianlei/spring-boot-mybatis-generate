package com.example.demo.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;


/**
 * 创建人:连磊
 * 日期: 2018/11/21. 15:40
 * 描述：开启一个线程
 */
@Configuration
@EnableAsync
public class HttpRequestExecutePool implements Runnable {

    private String path;

    private List<String> page;

    @Override
    public void run() {

    }
}
