package com.example.productService.services;

import com.example.productService.dto.FakeStoreProductDto;
import com.example.productService.models.Product;

import java.util.List;

//factory
    public interface ProductService {

    Product getSingleProduct(Long productId);

    List<Product> getAllProducts();

    Product addNewProduct(Product product);

    Product replaceProduct(Long id, Product product);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);

}
