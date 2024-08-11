package com.ecommerce.productservice.services;

import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryServiceInterface {
    Category getSingleCategory(Long id) throws CategoryNotFoundException;
    List<Category> getAllCategories();
}
