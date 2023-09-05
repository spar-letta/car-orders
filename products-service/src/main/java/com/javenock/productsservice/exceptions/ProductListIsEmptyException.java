package com.javenock.productsservice.exceptions;

public class ProductListIsEmptyException extends Exception{
    public ProductListIsEmptyException(String message) {
        super(message);
    }
}
