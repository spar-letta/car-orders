package com.javenock.orderservice.controller;

import com.javenock.orderservice.exception.NoOrderFoundException;
import com.javenock.orderservice.exception.NoOrdersMadeException;
import com.javenock.orderservice.exception.OrderPlacingFailException;
import com.javenock.orderservice.model.Order;
import com.javenock.orderservice.request.OrderRequest;
import com.javenock.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/orders")
@RestController
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "order", fallbackMethod = "serviceOrderFallBackMethod")
    public String makeNewOrder(@RequestBody OrderRequest orderRequest) throws OrderPlacingFailException {
        double totalCharge = orderService.makeNewOrder(orderRequest);
        return "Pay Ksh."+totalCharge +" for the order number "+orderRequest.getOrderNumber()+ " to be processed";
    }

    public String serviceOrderFallBackMethod(OrderRequest orderRequest, Exception e){
        return "Service call is too long to respond, try again later";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> allOrders() throws NoOrdersMadeException {
        return orderService.getAllOrders();
    }

    @GetMapping("/order/{orderNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Order getByOrderNumber(@PathVariable String orderNumber) throws NoOrderFoundException {
        return orderService.getByOrderNumber(orderNumber);
    }

    @PutMapping("/cancel-order/{orderNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Order cancelOrder(@PathVariable String orderNumber) throws NoOrderFoundException {
        return orderService.cancelOrder(orderNumber);
    }

}
