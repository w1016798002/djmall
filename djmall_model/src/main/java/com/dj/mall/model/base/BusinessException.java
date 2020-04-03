package com.dj.mall.model.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 自定义-业务处理异常类
 * @author 86150
 */
@Data
public class BusinessException extends RuntimeException implements Serializable {

    private String errorMsg;

    private Integer errorCode = -1;

    public BusinessException() {}

    public BusinessException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public BusinessException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusinessException(Integer errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

}
