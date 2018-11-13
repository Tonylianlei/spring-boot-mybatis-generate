package com.example.demo.service;

import com.example.demo.bean.model.DgUser;

import java.util.List;

/**
 * 创建人:连磊
 * 日期: 2018/11/13. 14:24
 * 描述：
 */
public interface UserService {

    List<DgUser> getUser();

    void register();

}
