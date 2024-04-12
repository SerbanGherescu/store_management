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
        return "redirect:/category/createNewCategory?success";
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        // Check if the authenticated user has the necessary role
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            // Return a 403 Forbidden response if the user is not authorized
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Perform the deletion logic
        categoryService.deleteCategoryByID(id);

        // Return a 204 No Content response upon successful deletion
        return ResponseEntity.noContent().build();
    }
}
