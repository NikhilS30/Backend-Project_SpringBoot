package com.example.productService.services;

import com.example.productService.customExceptions.ProductNotfoundException;
import com.example.productService.models.Category;
import com.example.productService.models.Product;
import com.example.productService.repositories.CategoryRepository;
import com.example.productService.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
//@Primary //now making this primary
public class SelfProductService implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

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
    public Page<Product> getAllProducts(int pageNumber, int pageSize) {
        Sort sort = Sort.by("price").ascending().and(Sort.by("title").descending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
               return  productRepository.findAll(pageable);
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
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
        Optional<Product> optionalProductInDb = productRepository.findById(id);
        if(optionalProductInDb.isEmpty()){
            throw new ProductNotfoundException("Product doesn't exist with given id "+ id);
        }
        Product productInDB =  optionalProductInDb.get();

        if(product.getPrice()!=null){
            productInDB.setPrice(product.getPrice());
        }

        if(product.getTitle()!=null){
            productInDB.setTitle(product.getTitle());
        }

        if(product.getCategory()!=null){
            productInDB.setCategory(product.getCategory());
        }
        return productRepository.save(productInDB);
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
