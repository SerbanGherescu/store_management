package com.example.store_management.service;

import com.example.store_management.entity.Product;
import com.example.store_management.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {

        this.productRepo = productRepo;

    }

    public void saveProduct(Product product) {

        productRepo.save(product);
        System.out.println("Product "
                + product.getName()
                + " successfully created!");

    }

    public Product searchProductByName(String name) {

        Product productFound = productRepo.findByName(name);

        return productFound;

    }

    public Optional<Product> findProductById(Long id) {

        Optional<Product> productFound = productRepo.findById(id);

        return productFound;

    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public void deleteProductByName(String name) {
        productRepo.deleteByName(name);
        System.out.println("Product with "
                + name
                + " successfully deleted!");
    }

    public void deleteProductById(Long id) {
        productRepo.deleteById(id);
        System.out.println("Product with id "
                + id
                + " successfully deleted!" );
    }

}
