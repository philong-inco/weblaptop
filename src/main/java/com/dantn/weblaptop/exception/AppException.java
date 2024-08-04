package com.dantn.weblaptop.exception;

import lombok.Getter;

@Getter
public class AppException extends Exception {
    private  ErrorCode errorCode;
    public AppException( ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode= errorCode;
    }
}
