package com.example.store_management.service;

import com.example.store_management.entity.Category;
import com.example.store_management.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;

    @Autowired
    private CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }



}
