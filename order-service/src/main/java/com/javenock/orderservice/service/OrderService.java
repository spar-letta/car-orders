package com.javenock.orderservice.service;

import com.javenock.orderservice.exception.NoOrderFoundException;
import com.javenock.orderservice.exception.NoOrdersMadeException;
import com.javenock.orderservice.exception.OrderPlacingFailException;
import com.javenock.orderservice.model.Order;
import com.javenock.orderservice.model.OrderDetails;
import com.javenock.orderservice.repository.OrderDetailsRepository;
import com.javenock.orderservice.repository.OrderRepository;
import com.javenock.orderservice.request.OrderDetailsRequest;
import com.javenock.orderservice.request.OrderRequest;
import com.javenock.orderservice.response.ProductResponse;
import com.javenock.orderservice.utils.ArithmeticUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaTemplate<String,String > payNotification;

    @Transactional
    public double makeNewOrder(OrderRequest orderRequest) throws OrderPlacingFailException {
        boolean isInStock = false;
        Double sumOfAmountToPay = 0.0;
        // check quantity in stock
        List<String> productCode = orderRequest.getProductRequestList().stream().map(prod -> prod.getProductCode()).collect(Collectors.toList());
        ResponseEntity<Boolean> listResponse = restTemplate.postForEntity("http://PRODUCT-SERVICE:8082/products/product-in-stock", productCode, Boolean.class);

        // save order details
        if(listResponse.getBody().booleanValue()){
            sumOfAmountToPay = orderRequest.getProductRequestList().stream().map(prod -> prod.getQuantityOrdered() * prod.getPriceEach()).reduce((double) 0, ArithmeticUtils::add);
            Message<String> paymessage = MessageBuilder.withPayload(String.valueOf(sumOfAmountToPay))
                    .setHeader(KafkaHeaders.TOPIC, "payment-report")
                    .build();
            payNotification.send(paymessage);
            List<OrderDetails> lists = orderRequest.getProductRequestList().stream().map(prod ->
                    OrderDetails.builder().orderNumber(orderRequest.getOrderNumber())
                            .productCode(prod.getProductCode())
                            .quantityOrdered(prod.getQuantityOrdered())
                            .priceEach(prod.getPriceEach()).build()
                    ).collect(Collectors.toList());
            orderDetailsRepository.saveAll(lists);
            Order order = Order.builder()
                    .orderNumber(orderRequest.getOrderNumber())
                    .orderDate(LocalDate.now())
                    .status("IN PROCESS")
                    .comment(orderRequest.getComment())
                    .customerNo(orderRequest.getCustomerNumber())
                    .totalAmountToBePaid(sumOfAmountToPay)
                    .build();
            orderRepository.save(order);

        }
        return sumOfAmountToPay;
    }

    public List<Order> getAllOrders() throws NoOrdersMadeException {
        List<Order> all_orders = orderRepository.findAll();
        if(all_orders.size() > 0){
            return all_orders;
        }else{
            throw new NoOrdersMadeException("Zero orders made");
        }
    }

    public Order getByOrderNumber(String orderNumber) throws NoOrderFoundException {
        return orderRepository.findByOrderNumber(orderNumber).orElseThrow(() -> new NoOrderFoundException("No order found with orderNumber :"+ orderNumber));
    }

    public Order cancelOrder(String orderNumber) throws NoOrderFoundException {
        Order order = orderRepository.findByOrderNumber(orderNumber).orElseThrow(() -> new NoOrderFoundException("No order found with orderNumber :"+ orderNumber));
        order.setStatus("CANCELLED");
        order.setTotalAmountToBePaid(0);
        return orderRepository.save(order);
    }


}
