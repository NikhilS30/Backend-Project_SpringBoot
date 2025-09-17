package com.example.productService.controllers;

import com.example.productService.models.Product;
import com.example.productService.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //this annotation helps in telling compiler that this is a rest annotation
@RequestMapping("/api") //this is class's path
public class ProductController {

    private final ProductService productService;

     ProductController(@Qualifier("fakeStoreProductService") ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/helloWorld/{name}/{times}")
    public String firstApi(@PathVariable String name,@PathVariable ("times") int times){
         String output = "";
         for(int i = 1;i<=times;i++){
             output+= "hello world " + name + "\n";
         }
         return output;
    }


    /*
    ye ek path variable ka example hai --> means ki whatever value is coming in {}
    please usko put kardo @PathVariable ki annotation mein aur maine jo Long productId likha hai
    that means ki jo id path mein pass hogi wo yaha isme receive hogi
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable("id") Long productId){
        ResponseEntity<Product> responseEntity = null;
            Product product = productService.getSingleProduct(productId);
            responseEntity = new ResponseEntity<>(
                    product,
                    HttpStatus.OK
            );
        return responseEntity;
    }

//    @GetMapping("/products")
//    public List<Product> getAllProducts(){
//        return productService.getAllProducts();
//    }

    @GetMapping("/products")
    public Page<Product> getAllProducts(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize){
        return productService.getAllProducts(pageNumber,pageSize);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("product with id "+productId+" deleted successfully");
    }

    @PostMapping("/products")
    public Product createProducts(@RequestBody Product product){
        return productService.addNewProduct(product);
    }

    //patch api partial update product
    //Response entity is nothing bas ye hai ki hum ne ek hi response body mein status aur response add karke bhej diya.
    @PatchMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable ("id") Long id,@RequestBody Product product){
        ResponseEntity<Product> responseEntity = new ResponseEntity<>(
                productService.updateProduct(id,product),
                HttpStatus.OK
        );
         return responseEntity;
    }

    //put api update complete product
    @PutMapping("/products/{id}")
    public Product replaceProduct(@PathVariable ("id") Long id,@RequestBody Product product){
         return productService.replaceProduct(id,product);
    }
}