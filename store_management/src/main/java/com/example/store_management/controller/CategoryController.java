package com.example.store_management.controller;

import com.example.store_management.entity.Category;
import com.example.store_management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/listOfCategories")
    public ModelAndView showListOfCategories() {
        List<Category> categories = categoryService.getAllCategories();
        System.out.println("Number of categories: " + categories.size());
        ModelAndView mav = new ModelAndView("listOfCategories");
        mav.addObject("categories", categories);
        return mav;
    }

    @GetMapping("/createNewCategory")
    public String CreateNewCategoryForm(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "createNewCategory";
    }

    @PostMapping("/createNewCategory")
    public String createNewCategory(@Validated @ModelAttribute("category")
                                    Category category,
                                    BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("Some error appear when trying to create new category!");
            return "createNewCategory";
        } else {
            Category existingCategory = categoryService.searchCategoryByName(category.getName());
            if (existingCategory != null
                    && existingCategory.getName() != null
                    && existingCategory.getName().isEmpty()) {
                result.rejectValue("name", null, "Category with this name "
                        + category.getName()
                        + " already exists!");
            } else {
                categoryService.saveCategory(category);
            }
        }
        return "redirect:/category/createNewCategory?success";
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        // Checking if users has necessary role
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        categoryService.deleteCategoryByID(id);

        return ResponseEntity.noContent().build();
    }
}
