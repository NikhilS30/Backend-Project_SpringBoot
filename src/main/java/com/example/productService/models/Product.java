package com.example.productService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

//@Entity(name = "Products") if you wish to give some name other than class name for db tables this is how you do
//whatever name you give it will make table in small case onl
@Entity
public class Product extends BaseModel {
    private String title;
    private Double price;
    @ManyToOne
    private Category category;
    /*
    @ManyToOne(fetch = FetchType.EAGER)
    agar muje product response mein category details nahi dikhani to mai fetch type ko lazy karduga
    isse bas mere product ki details ayegi uske sath mapped category nahi
    */
}