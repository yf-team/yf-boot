package com.yf.base.api.exception;


import com.yf.base.api.api.ApiError;
import com.yf.base.api.api.ApiRest;
import lombok.Data;


/**
 * 通用异常处理类
 * @author bool
 */
@Data
public class ServiceException extends RuntimeException{

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误消息
     */
    private String msg;


    /**
     * 从结果初始化
     * @param apiRest
     */
    public ServiceException(ApiRest apiRest){
        this.code = apiRest.getCode();
        this.msg = apiRest.getMsg();
    }

    /**
     * 从枚举中获取参数
     * @param apiError
     */
    public ServiceException(ApiError apiError){
        this.code = apiError.getCode();
        this.msg = apiError.msg;
    }

    /**
     * 通用的错误信息
     * @param msg
     */
    public ServiceException(String msg){
        this.code = 1;
        this.msg = msg;
    }


    @Override
    public String getMessage(){
        return this.msg;
    }

}
