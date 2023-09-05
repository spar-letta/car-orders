package com.javenock.orderservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailsRequest {
    @NotBlank
    private String orderNumber;
    @NotBlank
    private String productCode;
    @Min(1)
    private Long quantityOrdered;
    @Min(1)
    private double priceEach;
}
