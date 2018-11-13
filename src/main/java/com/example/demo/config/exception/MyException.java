package com.example.demo.config.exception;

import com.example.demo.config.result.ResultCode;

/**
 * 创建人:连磊
 * 日期: 2018/11/13. 15:43
 * 描述：
 */
public class MyException extends RuntimeException {

    private Integer code;

    public MyException() {
    }

    public MyException(ResultCode resultCode){
        super(resultCode.getMs());
        code = resultCode.getCode();
    }

    public MyException(Integer code , String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
