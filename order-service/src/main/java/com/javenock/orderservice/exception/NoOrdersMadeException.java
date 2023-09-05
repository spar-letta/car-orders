package com.javenock.orderservice.exception;

public class NoOrdersMadeException extends Exception{
    public NoOrdersMadeException(String message) {
        super(message);
    }
}
