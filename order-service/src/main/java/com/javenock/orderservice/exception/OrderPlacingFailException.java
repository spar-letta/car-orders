package com.javenock.orderservice.exception;

public class OrderPlacingFailException extends Exception{
    public OrderPlacingFailException(String message) {
        super(message);
    }
}
