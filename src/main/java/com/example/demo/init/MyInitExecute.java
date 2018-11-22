package com.example.demo.init;

import com.example.demo.util.Reptilian;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 创建人:连磊
 * 日期: 2018/11/22. 17:49
 * 描述：
 */
@Component
public class MyInitExecute implements CommandLineRunner {
    @Override
    public void run(String... strings) throws Exception {
        String url = "http://pic.sogou.com";
        Reptilian.getUrl(url);
    }
}
