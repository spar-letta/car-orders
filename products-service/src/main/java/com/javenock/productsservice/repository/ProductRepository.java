package com.javenock.productsservice.repository;

import com.javenock.productsservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductCode(String productCode);

    List<Product> findAllByProductCodeIn(List<String> productCode);
}
