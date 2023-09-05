package com.javenock.productsservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "products", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"productCode"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
