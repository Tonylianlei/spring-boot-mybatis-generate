package com.example.demo.bean.model;

import java.util.Date;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
/**
 * 创建时间：2018-11-13	14:22:51.142
 * 创 建 者：Tony
 * 实体说明：用户信息
 */
@Data
@JsonIgnoreProperties(value = {"createTime","updateTime","deleteState","updateId","updateId"})
@ApiModel(description = "用户信息")
public class DgUser {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "中文名称")
    private String cnName;

    @ApiModelProperty(value = "英文名称")
    private String enName;

    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "用户状态")
    private Integer userState;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "注册日期")
    private Date registerTime;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "创建日期" , hidden = true)
    private Date createTime;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "修改日期" , hidden = true)
    private Date updateTime;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "删除状态" , hidden = true)
    private Integer deleteState;
}