package com.ecommerce.productservice.services;


import com.ecommerce.productservice.exceptions.ProductNotExistsException;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.repositories.CategoryRepository;
import com.ecommerce.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
@Service("selfProductService")
public class SelfProductService implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SelfProductService(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotExistsException { // In Class

        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()) {
            throw new ProductNotExistsException("Product with id: " + id + " doesn't exist.");
        }

        Product product = productOptional.get();

        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    public Product updateProduct(Long id, Product product) throws ProductNotExistsException { // In Class
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ProductNotExistsException(
                    "Product with id: " + id + " doesn't exist."
            );
        }
        Product existingProduct = optionalProduct.get();
        if(product.getId()!=null){
            existingProduct.setId(product.getId());
        }
        if(product.getDescription()!=null){
            existingProduct.setDescription(product.getDescription());
        }
        if(product.getTitle()!=null){
            existingProduct.setTitle(product.getTitle());
        }
        if (product.getPrice()!=null){
            existingProduct.setPrice(product.getPrice());
        }
        if (product.getImageUrl()!=null){
            existingProduct.setImageUrl(product.getImageUrl());
        }
        return productRepository.save(existingProduct);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product addNewProduct(Product product) {// In Class
      Optional<Category> optionalCategory = categoryRepository.findByName(product.getCategory().getName());
      if (optionalCategory.isEmpty()){
          product.setCategory(categoryRepository.save(product.getCategory()));
      }
      else {
          product.setCategory(optionalCategory.get());
      }

        return productRepository.save(product);
    }

    @Override
    public Product deleteProduct(Long id) throws ProductNotExistsException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()){
            throw new ProductNotExistsException("Product with id: " + id + " doesn't exist.");
        }
        Product product = optionalProduct.get();
        productRepository.deleteById(id);
        return product;
    }
}
