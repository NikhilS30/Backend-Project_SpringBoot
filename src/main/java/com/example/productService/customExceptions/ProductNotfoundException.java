package com.example.productService.customExceptions;

public class ProductNotfoundException extends RuntimeException{
    public ProductNotfoundException(String message){
        super(message);
    }
}