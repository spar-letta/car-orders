package com.javenock.orderservice.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PayNotificationConsumer {
    Logger LOGGER = LoggerFactory.getLogger(PayNotificationConsumer.class);

    @KafkaListener(topics = "payment-report", groupId = "payment-notification-v1")
    public void readBillReportMessage(String paynotification){
        LOGGER.info("Payment Notification Send from Kafka, Pay Ksh.{} for your order to be processed.", paynotification);
    }
}
