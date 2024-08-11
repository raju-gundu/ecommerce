package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.exceptions.ProductNotExistsException;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.repositories.ProductRepository;
import com.ecommerce.productservice.services.ProductService;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//@SpringBootTest
class ProductControllerTest {
//    @Autowired
//    private ProductController productController;
//
//    @MockBean
//    private ProductService productService;
//
//    @MockBean
//    private ProductRepository productRepository;
//
//    @Test
//    void testProductsSameAsService(){
//
//        //arrange
//        List<Product> products = new ArrayList<>();
//
//        Product product1 = new Product();
//        product1.setTitle("iphone 15");
//        products.add(product1);
//
//        Product product2 = new Product();
//        product2.setTitle("iphone 15 pro");
//        products.add(product2);
//
//        Product product3 = new Product();
//        product3.setTitle("iphone 15 pro max");
//        products.add(product3);
//        when(productService.getAllProducts()).thenReturn(products);
//
//        //act
////        ResponseEntity<List<Product>> response = productController.getAllProducts();
//
//        //assert
////        List<Product> responseBody = response.getBody();
////
////        assertEquals(products.size(), responseBody.size());
//

//    }
//
//    @Test
//    void testProductsSameAsRepository() throws ProductNotExistsException {
//        when(
//                productRepository.findById(10L)
//        ).thenReturn(
//                Optional.empty()
//        );
//
//        when(productService.getSingleProduct(10L)).thenCallRealMethod();
//
//        assertThrows(
//                ProductNotExistsException.class,
//                () -> productController.getSingleProduct(10L)
//        );
//
//    }
}