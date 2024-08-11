package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getSingleCategory(@PathVariable("id") Long id) throws CategoryNotFoundException {
        return new ResponseEntity<>(categoryService.getSingleCategory(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories(),HttpStatus.OK);
    }
}
