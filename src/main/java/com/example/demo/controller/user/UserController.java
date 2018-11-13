package com.example.demo.controller.user;

import com.example.demo.config.result.ResultMessage;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创建人:连磊
 * 日期: 2018/11/13. 14:23
 * 描述：
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register" , method = RequestMethod.POST)
    public ResultMessage register(){
        return ResultMessage.setResult(userService.getUser());
    }

}
