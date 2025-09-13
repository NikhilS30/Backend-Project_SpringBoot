package com.example.productService.controllers;

import com.example.productService.controllers.ProductController;
import com.example.productService.models.Product;
import com.example.productService.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void testGetSingleProduct() {
    }

    @Test
    void testGetSingleProductPositive() {
    }

    @Test
    void testGetSingleProductNegative() {
    }

    @Test
    void getAllProductsTest() {
        Product p1 = new Product();
        p1.setTitle("iPhone 15");
        p1.setPrice(1500.0);

        Product p2 = new Product();
        p2.setTitle("iPhone 16");
        p2.setPrice(1600.0);

        Product p3 = new Product();
        p3.setTitle("iPhone 17");
        p3.setPrice(1700.0);

        List<Product> expectedProducts = Arrays.asList(p1, p2, p3);

        when(productService.getAllProducts()).thenReturn(expectedProducts);

        List<Product> actualProducts = productController.getAllProducts();

        assertEquals(expectedProducts.size(), actualProducts.size());

        for (int i = 0; i < expectedProducts.size(); i++) {
            assertEquals(expectedProducts.get(i).getTitle(), actualProducts.get(i).getTitle());
            assertEquals(expectedProducts.get(i).getPrice(), actualProducts.get(i).getPrice());
        }
    }
}