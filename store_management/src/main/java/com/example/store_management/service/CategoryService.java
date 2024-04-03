package com.example.store_management.service;

import com.example.store_management.entity.Category;
import com.example.store_management.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public void saveCategory(Category category) {

        categoryRepo.create(category);
        System.out.println("Category "
                + category.getName()
                + " successfully created!");

    }

    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    public Category searchCategoryByName(String name) {

        Category searchCategory = categoryRepo.findByName(name);

            return searchCategory;


    }

    public void deleteCategoryByName(String name) {

        categoryRepo.deleteByName(name);
        System.out.println("Category "
                + name
                + "successfully deleted!");

    }
}
