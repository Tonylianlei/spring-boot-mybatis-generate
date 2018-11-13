package com.example.demo.bean.model;

import java.util.Date;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
/**
 * 创建时间：2018-11-13	14:22:51.127
 * 创 建 者：Tony
 * 实体说明：用户基础信息
 */
@Data
@JsonIgnoreProperties(value = {"createTime","updateTime","deleteState","updateId","updateId"})
@ApiModel(description = "用户基础信息")
public class DgUserInfo {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "邮箱")
    private String mailbox;

    @ApiModelProperty(value = "手机")
    private String tel;

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