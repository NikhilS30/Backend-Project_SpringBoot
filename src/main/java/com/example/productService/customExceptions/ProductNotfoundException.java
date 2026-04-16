package com.example.productService.customExceptions;

public class ProductNotfoundException extends RuntimeException {

    // constructor with message
    public ProductNotfoundException(String message) {
        super(message);
    }

    // constructor with id
    public ProductNotfoundException(Long id) {
        super("Product with id " + id + " does not exist");
    }
}