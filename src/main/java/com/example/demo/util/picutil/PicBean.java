package com.example.demo.util.picutil;

import lombok.Data;

/**
 * 创建人:连磊
 * 日期: 2018/11/27. 9:48
 * 描述：
 */
@Data
public class PicBean {

    private String title;

    private String url;

    private String path;

    public PicBean(String title, String url, String path) {
        this.title = title;
        this.url = url;
        this.path = path;
    }
}
