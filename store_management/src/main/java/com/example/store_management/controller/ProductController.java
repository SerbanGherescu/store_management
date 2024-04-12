package com.example.store_management.controller;

import com.example.store_management.entity.Category;
import com.example.store_management.entity.Product;
import com.example.store_management.service.CategoryService;
import com.example.store_management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {

        this.categoryService = categoryService;
        this.productService = productService;

    }

    @GetMapping("/listOfProducts/{id}")
    public ModelAndView showListOfProducts(@PathVariable Long id) {

        ModelAndView mav = new ModelAndView("listOfProducts");
        Category category = categoryService.getCategoryById(id);
        System.out.println(category);
        List<Product> product = productService.findProductByCategoryId(id);

        mav.addObject("category", category);
        mav.addObject("product",product);

        return mav;

    }

    @GetMapping("/createNewProduct")
    public String createNewProductForm(Model model) {
        Product product = new Product();
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "createNewProduct";
    }

    @PostMapping("/createNewProduct")
    public String createNewProduct(@Validated @ModelAttribute("product")
                                   Product product,
                                   @RequestParam(name = "categories", required = false) Long id,
                                   BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("Some error appear when trying to create new product!");
            return "createNewProduct";
        } else {
            Product existingProduct = productService.searchProductByName(product.getName());
            if (existingProduct != null
                    && existingProduct.getName() != null
                    && existingProduct.getName().isEmpty()) {
                result.rejectValue("name", null, "Product with this name "
                        + product.getName()
                        + " already exists!");
            } else {
                // Check if id is not null before using it
                if (id != null) {
                    // Fetch the category by id
                    Category category = categoryService.getCategoryById(id);
                    if (category != null) {
                        // Set the category to the product
                        product.setCategory(category);
                    }
                }
                productService.saveProduct(product);
            }
            return "redirect:/product/createNewProduct?success";
        }
    }

    @PostMapping("/updateStock/{id}")
    public String updateStock(@PathVariable Long id, @RequestParam boolean newStockStatus, RedirectAttributes redirectAttributes) {
        productService.updateProductStock(id, newStockStatus);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/product/listOfProducts/{id}";
    }

    @PostMapping("/updateQuantity/{id}")
    public String updateQuantity(@PathVariable Long id, @RequestParam int newQuantity, RedirectAttributes redirectAttributes) {
        productService.updateProductQuantity(id, newQuantity);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/product/listOfProducts/{id}";
    }

    @PostMapping("/updatePrice/{id}")
    public String updatePrice(@PathVariable Long id, @RequestParam double newPrice, RedirectAttributes redirectAttributes) {
        productService.updateProductPrice(id, newPrice);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/product/listOfProducts/{id}";
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }
}
