package com.javenock.orderservice.repository;

import com.javenock.orderservice.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderDetailsRepositoryTest extends JpaRepository<OrderDetails, UUID> {
}
