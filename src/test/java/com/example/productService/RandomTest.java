package com.example.productService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomTest {

    @Test
    void testAddMethod(){
        int a = 10;
        int b = 20;

        int result = a+b+1;

        assertEquals(30, result, "result should be 30 there is some logic error");

//        assert result==30;
    }
}
