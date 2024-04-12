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
        mav.addObject("product", productService.getAllProducts());

        return mav;

    }

    @GetMapping("/createNewProduct")
    public String createNewProductForm(Model model) {

        Product product = new Product();
        model.addAttribute("product", product);

        return "createNewProduct";

    }

    @PostMapping("/createNewProduct")
    public String createNewProduct(@Validated @ModelAttribute("product")
                                    Product product,
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
                productService.saveProduct(product);
            }
            return "redirect:/product/createNewProduct?success";
        }
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }
}
