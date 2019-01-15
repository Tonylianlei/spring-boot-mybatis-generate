package com.example.demo.controller.status;

import com.example.demo.bean.model.DgUser;
import com.example.demo.config.result.ResultMessage;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创建人:连磊
 * 日期: 2018/11/13. 14:23
 * 描述：
 */
@Api(value = "2用户信息查询", description = "用户基本信息操作API", tags = "用户信息查询")
@RestController
public class StarusController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "根据姓名查询用户信息", notes = "根据姓名查询用户信息 ")
    @RequestMapping(value = "/registersss" , method = RequestMethod.POST)
    public ResultMessage register(@RequestBody DgUser dgUser){
        return ResultMessage.setResult(userService.getUser());
    }

}
