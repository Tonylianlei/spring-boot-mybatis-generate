package com.example.demo.util;

import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 创建人:连磊
 * 日期: 2018/11/21. 15:40
 * 描述：开启一个线程
 */
@Configuration
@EnableAsync
public class HttpRequestExecutePool implements Runnable {

    private Map<String , String> path = new HashedMap();

    public HttpRequestExecutePool(Map<String, String> path) {
        this.path = path;
    }

    @Override
    public void run() {
        List<String> pathList = new ArrayList<>(path.keySet());
        pathList.forEach(paths -> {
            JSONObject gbk = HttpRequest.getJSONObject(HttpRequest.sendGet("https://pic.sogou.com/pics/channel/getAllRecomPicByTag.jsp", paths, "GBK"));
            HttpRequest.getInfo(gbk , path.get(paths));
        });
    }
}
