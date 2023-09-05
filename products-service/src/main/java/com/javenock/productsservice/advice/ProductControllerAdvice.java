package com.javenock.productsservice.advice;

import com.javenock.productsservice.exceptions.NoSuchProductException;
import com.javenock.productsservice.exceptions.ProductListIsEmptyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ProductControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchProductException.class)
    public Map<String, String> handleNoSuchProductException(NoSuchProductException exception){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("System message :", exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProductListIsEmptyException.class)
    public Map<String, String> handleProductListIsEmptyException(ProductListIsEmptyException exception){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("System message :", exception.getMessage());
        return errorMap;
    }

}
