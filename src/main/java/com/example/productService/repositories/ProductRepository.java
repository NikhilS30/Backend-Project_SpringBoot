package com.example.productService.repositories;


import com.example.productService.models.Product;
import com.example.productService.projections.ProductWithIdAndTitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional <Product> findProductById(Long id);

    List<Product> findProductByTitle(String title);

    @Override
    Page<Product> findAll(Pageable pageable);

    Product save(Product product);

    //ye ek hql query hai isme hum projection kar rahe hai jiska humne interface mein use kiya hai
    //ProductWithIdAndTitle ye ek interface jisme hum iski working likhege
    @Query("select p.id,p.title from Product p where p.id = 1")
    ProductWithIdAndTitle randomSearch();
}
