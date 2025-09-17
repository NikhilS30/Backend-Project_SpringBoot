package com.example.productService.services;

import com.example.productService.customExceptions.ProductNotfoundException;
import com.example.productService.dto.FakeStoreProductDto;
import com.example.productService.models.Category;
import com.example.productService.models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
//@Primary
public class FakeStoreProductService implements ProductService{
    private static final Logger logger = LoggerFactory.getLogger(FakeStoreProductService.class);

    private RestTemplate restTemplate;
    private RedisTemplate<String,Object> redisTemplate;
    private FakeStoreProductDto fakeStoreProductDto;

    FakeStoreProductService(RestTemplate restTemplate, RedisTemplate<String,Object> redisTemplate){
        this.restTemplate=restTemplate;
        this.redisTemplate = redisTemplate;

    }

    @Override
    public Product getSingleProduct(Long productId){
        Product redisproduct =  (Product) redisTemplate.opsForHash().get("PRODUCTS","PRODUCT_" + productId);
        if(redisproduct!=null){
            //cache hit
            return redisproduct;
        }
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/"+productId,
                FakeStoreProductDto.class
                );

        logger.info("fakeStore product response :: {}",fakeStoreProductDto);

        if(fakeStoreProductDto == null){
            throw new ProductNotfoundException("Product with id "+ productId +" doesn't exist");
        }
        Product product = convertFakeStoreProductToProduct(fakeStoreProductDto);


        //storing data in redis db
        redisTemplate.opsForHash().put("PRODUCTS","PRODUCT_" +productId , product);
        return product;
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductDtoList = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class
        );
        List<Product> productList = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto1 : fakeStoreProductDtoList){
            productList.add(convertFakeStoreProductToProduct(fakeStoreProductDto1));
        }
        return productList;
    }

    @Override
    public Product addNewProduct(Product product) {
        return null;
    }


    @Override
    //Partial Update
    public Product updateProduct(Long id, Product product) {
        //PATCH
        RequestCallback requestCallback = restTemplate.httpEntityCallback(product, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor = new HttpMessageConverterExtractor(
                FakeStoreProductDto.class,
                restTemplate.getMessageConverters());

        FakeStoreProductDto response = restTemplate.execute(
                "https://fakestoreapi.com/products/" + id,
                HttpMethod.PATCH,
                requestCallback,
                responseExtractor);

        return convertFakeStoreProductToProduct(response);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(product);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(
                "https://fakestoreapi.com/products/{id}",
                HttpMethod.PUT,
                requestCallback,
                restTemplate.responseEntityExtractor(FakeStoreProductDto.class),
                id
        );

        FakeStoreProductDto fakeStoreProductDto1 =  response.getBody();
        return convertFakeStoreProductToProduct(fakeStoreProductDto1);
    }


    @Override
    public void deleteProduct(Long id) {
        restTemplate.delete(
                "https://fakestoreapi.com/products/{id}",id
        );
    }

    private Product convertFakeStoreProductToProduct(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());

        Category category = new Category();
        category.setDescription(fakeStoreProductDto.getCategory());
        product.setCategory(category);

        return product;
    }
}