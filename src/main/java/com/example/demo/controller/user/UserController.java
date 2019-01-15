package com.example.demo.controller.user;

import com.example.demo.config.result.ResultMessage;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创建人:连磊
 * 日期: 2018/11/13. 14:23
 * 描述：
 */
@Api(value = "1用户信息查询", description = "用户基本信息操作API", tags = "用户信息查询")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "根据姓名查询用户信息", notes = "根据姓名查询用户信息 ")
    @RequestMapping(value = "/register" , method = RequestMethod.POST)
    public ResultMessage register(){
        return ResultMessage.setResult(userService.getUser());
    }

}
