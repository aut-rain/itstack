package com.zzy.config_factory;

public class PayConfigNotFoundException extends RuntimeException{
    public PayConfigNotFoundException(String message) {
        super(message);
    }
    public PayConfigNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
