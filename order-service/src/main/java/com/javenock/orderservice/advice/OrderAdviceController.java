package com.javenock.orderservice.advice;

import com.javenock.orderservice.exception.NoOrderFoundException;
import com.javenock.orderservice.exception.NoOrdersMadeException;
import com.javenock.orderservice.exception.OrderPlacingFailException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class OrderAdviceController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoOrdersMadeException.class)
    public Map<String, String> handleNoOrdersMadeException(NoOrdersMadeException exception){
        Map<String, String> mapError = new HashMap<>();
        mapError.put("System message : ", exception.getMessage());
        return mapError;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoOrderFoundException.class)
    public Map<String, String> handleNoOrderFoundException(NoOrderFoundException exception){
        Map<String, String> mapError = new HashMap<>();
        mapError.put("System message : ", exception.getMessage());
        return mapError;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OrderPlacingFailException.class)
    public Map<String, String> handleOrderPlacingFailException(OrderPlacingFailException exception){
        Map<String, String> mapError = new HashMap<>();
        mapError.put("System message : ", exception.getMessage());
        return mapError;
    }
}
