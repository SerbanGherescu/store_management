package com.example.store_management.controller;

import com.example.store_management.entity.Category;
import com.example.store_management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

        ModelAndView mav = new ModelAndView("listOfCategories");
        mav.addObject("category", categoryService.getAllCategories());

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
        return "redirect:/createNewCategory?success";
    }

    @DeleteMapping("/category/{name}")
    public ResponseEntity<?> deleteCategoryByName(@PathVariable String name) {
        categoryService.deleteCategoryByName(name);
        return ResponseEntity.ok().build();
    }
}
