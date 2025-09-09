package com.example.productService.services;

import com.example.productService.models.Category;
import com.example.productService.models.Product;
import com.example.productService.repositories.CategoryRepository;
import com.example.productService.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class SelfProductService implements ProductService {

    Product product;

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository,CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findProductById(productId);
        if(optionalProduct.isEmpty()){
            throw new RuntimeException("product with id " +productId +" does not exist");
        }
//      Product product =optionalProduct.get();
        return optionalProduct.get();
    }

    @Override
    public List<Product> getAllProducts() {
       return productRepository.findAll();
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product addNewProduct(Product product) {
        Category category = product.getCategory();
        if(category.getId()==null){
            // this is new category or a new product toh pehle category save hogi then product
             Category savedCategory = categoryRepository.save(category);

             //ye humne islie kiya kyu ki jo nayi category save hui hai uski id ayegi saved category mein
             //aur hum uss updated saved category ko product mein save kardege id miljaegi na
             product.setCategory(savedCategory);
        }
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isEmpty()){
            throw new RuntimeException("Product does not exist");
        }

        //yee mera wo product hai jo ki db mein hai
        Product productIndb = optionalProduct.get();

        //aur muje ab jo product payload mein aya hai usko check karna hai aur ek ek karke update karna hai
        if(product.getPrice()!= null){
            productIndb.setPrice(product.getPrice());
        }
        if(product.getTitle()!=null){
            productIndb.setTitle(product.getTitle());
        }

        //ab maine update kardia hai product aur isko save karwa raha hu
        return productRepository.save(productIndb);
    }
}
