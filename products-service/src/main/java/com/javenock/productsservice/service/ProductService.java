package com.javenock.productsservice.service;

import com.javenock.productsservice.exceptions.NoSuchProductException;
import com.javenock.productsservice.exceptions.ProductListIsEmptyException;
import com.javenock.productsservice.model.Product;
import com.javenock.productsservice.repository.ProductRepository;
import com.javenock.productsservice.request.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createNewProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .productCode(productRequest.getProductCode())
                .productName(productRequest.getProductName())
                .productLine(productRequest.getProductLine())
                .productVendor(productRequest.getProductVendor())
                .productDescription(productRequest.getProductDescription())
                .quantityInStock(productRequest.getQuantityInStock())
                .buyPrice(productRequest.getBuyPrice())
                .build();
        return productRepository.save(product);
    }


    public Product getProductByCode(String productCode) throws NoSuchProductException {
        return productRepository.findByProductCode(productCode).orElseThrow(() -> new NoSuchProductException("No such product with product code : "+ productCode));
    }


    public List<Product> getListOfProduct() throws ProductListIsEmptyException {
        List<Product> allProducts = productRepository.findAll();
        if(allProducts.size() > 0){
            return allProducts;
        }else{
            throw new ProductListIsEmptyException("Zero products found.");
        }
    }

    public boolean isProductInStockIn(List<String> productString) {
        boolean isInStock = false;
        List<Product> productList = productRepository.findAllByProductCodeIn(productString);
        if(productList.size() == productString.size()){
            isInStock = productList.stream().allMatch(prod -> prod.getQuantityInStock() > 0 );
        }
        return isInStock;
    }
}
