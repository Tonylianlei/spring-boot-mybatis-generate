package com.example.demo.config.result;

/**
 * 创建人:连磊
 * 日期: 2018/11/13. 15:06
 * 描述：返回结果
 */
public class ResultMessage<T> {

    private T data;

    private String message;

    private Integer code;

    private static ResultMessage resultMessage =null;

    public static ResultMessage getInstance() {
        if (resultMessage == null) {
            synchronized (ResultMessage.class) {
                if (resultMessage == null) {
                    resultMessage = new ResultMessage();
                }
            }
        }
        return resultMessage;
    }

    public ResultMessage() {
        this.message = ResultCode.RESULT_SUCCESS.getMs();
        this.code = ResultCode.RESULT_SUCCESS.getCode();
    }

    public static ResultMessage setResult(Object t){
        ResultMessage resultMessage = getInstance();
        resultMessage.setData(t);
        return resultMessage;
    }

    public static ResultMessage setResult(ResultCode result){
        ResultMessage resultMessage = getInstance();
        resultMessage.setMessage(result.getMs());
        resultMessage.setCode(result.getCode());
        return resultMessage;
    }

    public static ResultMessage setCode(ResultCode resultCode){
        ResultMessage resultMessage = getInstance();
        resultMessage.setCode(resultCode.getCode());
        resultMessage.setMessage(resultMessage.getMessage());
        return resultMessage;
    }

    public static ResultMessage setResult(Integer code , String message){
        ResultMessage resultMessage = getInstance();
        resultMessage.setCode(code);
        resultMessage.setMessage(message);
        return resultMessage;
    }

    public  static ResultMessage setResult(Object data, String message, Integer code) {
        ResultMessage resultMessage = getInstance();
        resultMessage.setCode(code);
        resultMessage.setMessage(message);
        resultMessage.setData(data);
        return resultMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
