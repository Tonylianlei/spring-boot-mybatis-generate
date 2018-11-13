package com.example.demo.service.impl;

import com.example.demo.bean.mapper.DgUserInfoMapper;
import com.example.demo.bean.mapper.DgUserMapper;
import com.example.demo.bean.model.DgUser;
import com.example.demo.bean.model.DgUserExample;
import com.example.demo.bean.model.DgUserInfo;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 创建人:连磊
 * 日期: 2018/11/13. 14:25
 * 描述：
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private DgUserMapper dgUserMapper;
    @Autowired
    private DgUserInfoMapper dgUserInfoMapper;

    @Override
    public List<DgUser> getUser() {
        DgUserExample dgUserExample = new DgUserExample();
        return dgUserMapper.selectByExample(dgUserExample);
    }

    @Override
    public void register() {
        dgUserMapper.insert(new DgUser());
        DgUserInfo dgUserInfo = new DgUserInfo();
        dgUserInfo.setId("sdf");
        dgUserInfoMapper.insert(dgUserInfo);
    }
}
