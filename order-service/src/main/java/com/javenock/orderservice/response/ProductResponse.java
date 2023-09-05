package com.javenock.orderservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String productCode;
    private String productName;
    private String productLine;
    private String productVendor;
    @Column(columnDefinition = "TEXT")
    private String productDescription;
    private Long quantityInStock;
    private double buyPrice;
}
