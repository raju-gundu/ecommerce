package com.ecommerce.productservice.controllers;


import com.ecommerce.productservice.commons.AuthenticationCommons;
import com.ecommerce.productservice.exceptions.ProductNotExistsException;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private RestTemplate restTemplate;
    private AuthenticationCommons authenticationCommons;


    @Autowired
    public ProductController(@Qualifier("fakeStoreProductService") ProductService productService,
                             RestTemplate restTemplate, AuthenticationCommons authenticationCommons) {
        this.productService = productService;
        this.restTemplate = restTemplate;
        this.authenticationCommons = authenticationCommons;
    }

    @GetMapping() // localhost:8080/products
    public List<Product> getAllProducts() {

                return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable("id") Long id) throws ProductNotExistsException {
//        throw new RuntimeException("SOmething went wrong");
//        try {
            return new ResponseEntity<>(
                    productService.getSingleProduct(id),
                    HttpStatus.OK
            );

    }

    @PostMapping()
    public Product addNewProduct(@RequestBody Product product) {
        return productService.addNewProduct(product);
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) throws ProductNotExistsException {
        return productService.updateProduct(id,product);
    }

    @PutMapping("/{id}")
    public Product replaceProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        return new Product();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) throws ProductNotExistsException {
        return new ResponseEntity<>(productService.deleteProduct(id),HttpStatus.OK);
    }


}
