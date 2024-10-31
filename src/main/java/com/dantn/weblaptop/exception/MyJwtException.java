package com.dantn.weblaptop.exception;

public class MyJwtException extends RuntimeException {
    private int statusCode;

    public MyJwtException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
