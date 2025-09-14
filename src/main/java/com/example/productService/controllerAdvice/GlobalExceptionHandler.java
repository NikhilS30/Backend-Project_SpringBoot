package com.example.productService.controllerAdvice;

import com.example.productService.customExceptions.ProductNotfoundException;
import com.example.productService.dto.ExceptionDTO;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.RuntimeMBeanException;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(RuntimeMBeanException.class)
//    public ResponseEntity<ExceptionDTO> productNotfFound(){
//        ExceptionDTO exceptionDTO = new ExceptionDTO();
//        exceptionDTO.setMessage("Product does not exist");
//        exceptionDTO.setSolution("Try after sometime");
//        ResponseEntity<ExceptionDTO> response = new ResponseEntity<>(
//               exceptionDTO,
//                HttpStatus.NOT_FOUND
//        );
//        return response;
//    }

    @ExceptionHandler(ProductNotfoundException.class)
    public ResponseEntity<ExceptionDTO> productNotFound(ProductNotfoundException ex){
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setMessage(ex.getMessage());
        exceptionDTO.setSolution("Try after sometime");
        return new ResponseEntity<>(
                exceptionDTO,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> RuntimeException(){
        ResponseEntity<String> response = new ResponseEntity<>(
                "Runtime exception occurred",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return response;
    }
}
