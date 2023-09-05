package com.javenock.productsservice.exceptions;

public class NoSuchProductException extends Exception{
    public NoSuchProductException(String message) {
        super(message);
    }
}
