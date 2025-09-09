package com.example.productService.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//it is always better to have a request dto because in future if a payload changes or new
// parameter addition is required just add in dto thats it
public class CreateProductRequestDto{
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String imageUrl;
    private String category;
}
