package com.example.demo.config.result;

/**
 * 创建人:连磊
 * 日期: 2018/11/13. 14:49
 * 描述：返回信息code
 */
public enum  ResultCode {

    RESULT_SUCCESS(0 , "成功"),
    RESULT_USER_LOGIN_ERROR(1000 , "用户未登录"),
    RESULT_USER_DELETE_ERROR(1011 , "用户已被删除"),
    RESULT_USER_INSERT_ERROR(1012 , "用户已存在"),
    RESULT_USER_TOKEN_ERROR(1013 , "登录时间过期请重新登录"),
    RESULT_USER_LOGIN_ABNORMAL(1014 , "用户登录异常"),
    RESULT_MYSQL_CONNECT_ERROR(2011 , "数据库连接"),
    RESULT_PARAMETER_ERROR(2012 , "参数错误"),
    RESULT_ID_ERROR(2013 , "请求ID错误"),
    RESULT_REQUEST_NULL(2014 , "请求数据丢失,请补充完整"),
    RESULT_DATA_NULL(2015 , "数据不存在"),
    RESULT_INSERT_UPDATE_DATA_REPEAT(2017 , "当前数据已存在"),
    RESULT_DELETE_DATA_REPEAT(2017 , "当前数据已删除"),
    RESULT_FORMAT_DATA_REPEAT(2018 , "数据格式异常"),
    ;
    private Integer code;

    private String ms;

    ResultCode(Integer code, String ms) {
        this.code = code;
        this.ms = ms;
    }

    public Integer getCode() {
        return code;
    }

    public String getMs() {
        return ms;
    }
}
