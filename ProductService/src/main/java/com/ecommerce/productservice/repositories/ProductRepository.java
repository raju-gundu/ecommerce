package com.ecommerce.productservice.repositories;


import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.repositories.Projections.ProductWithIdAndTitle;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository
    extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

    Product save(Product product);

    @Query("select p.id as id,p.title as title,p.category.name as category from Product p where p.id=2")
    List<ProductWithIdAndTitle> xyz();
}