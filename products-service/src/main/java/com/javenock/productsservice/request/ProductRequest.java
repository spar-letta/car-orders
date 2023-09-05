package com.javenock.productsservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotBlank
    private String productCode;
    @NotBlank
    private String productName;
    @NotBlank
    private String productLine;
    @NotBlank
    private String productVendor;
    @NotBlank
    private String productDescription;
    @Min(1)
    private Long quantityInStock;
    @Min(1)
    private double buyPrice;
}
