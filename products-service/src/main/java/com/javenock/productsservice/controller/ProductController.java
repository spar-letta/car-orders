package com.javenock.productsservice.controller;

import com.javenock.productsservice.exceptions.NoSuchProductException;
import com.javenock.productsservice.exceptions.ProductListIsEmptyException;
import com.javenock.productsservice.model.Product;
import com.javenock.productsservice.request.ProductRequest;
import com.javenock.productsservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createNewProduct(@RequestBody @Valid ProductRequest productRequest){
        return productService.createNewProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getListOfProduct() throws ProductListIsEmptyException {
        return productService.getListOfProduct();
    }

    @GetMapping("/product/{productCode}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductByProductCode(@PathVariable String productCode) throws NoSuchProductException {
        return productService.getProductByCode(productCode);
    }

    @PostMapping("/product-in-stock")
    @ResponseStatus(HttpStatus.OK)
    public boolean isProductInStock(@RequestBody List<String> productString){
        return productService.isProductInStockIn(productString);
    }


}
