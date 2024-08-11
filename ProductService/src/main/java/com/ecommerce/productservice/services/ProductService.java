package com.ecommerce.productservice.services;

import com.ecommerce.productservice.exceptions.ProductNotExistsException;
import com.ecommerce.productservice.models.Product;


import java.util.List;

public interface ProductService {

    Product getSingleProduct(Long id) throws ProductNotExistsException, ProductNotExistsException;

    List<Product> getAllProducts();

    Product updateProduct(Long id, Product product) throws ProductNotExistsException;

    Product replaceProduct(Long id, Product product);

    Product addNewProduct(Product product);


    Product deleteProduct(Long id) throws ProductNotExistsException;
}
