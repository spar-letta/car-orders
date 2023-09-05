package com.javenock.orderservice.exception;

public class NoOrderFoundException extends Exception{
    public NoOrderFoundException(String message) {
        super(message);
    }
}
