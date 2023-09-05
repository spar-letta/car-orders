package com.javenock.orderservice.request;

import com.javenock.orderservice.model.ProductRequest;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
public class OrderRequest {
    @NotBlank
    private String orderNumber;
    private String comment;
    @Min(1)
    private Long customerNumber;
    private List<ProductRequest> productRequestList;
}
