package com.javenock.gatewayservice.exception;

public class UnAuthorizedException extends Exception{
    public UnAuthorizedException(String message) {
        super(message);
    }
}