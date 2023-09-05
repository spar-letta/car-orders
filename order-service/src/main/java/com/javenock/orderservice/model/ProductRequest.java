package com.javenock.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @NotBlank
    private String productCode;
    @Min(1)
    private Long quantityOrdered;
    @Min(1)
    private double priceEach;
}
