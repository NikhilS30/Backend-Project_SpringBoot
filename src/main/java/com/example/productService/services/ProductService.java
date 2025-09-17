package com.example.productService.services;

import com.example.productService.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

//factory
    public interface ProductService {

    Product getSingleProduct(Long productId);

    Page<Product> getAllProducts(int pageNumber, int pageSize);
    List<Product> getAllProducts();

    Product addNewProduct(Product product);

    Product replaceProduct(Long id, Product product);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);

}
