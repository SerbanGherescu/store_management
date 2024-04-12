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

    public List<Product> findProductByCategoryId(Long id) {

        return productRepo.findByCategoryId(id);

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

    public void updateProductStock(Long productId, boolean newStockStatus) {
        Product product = productRepo.getProductById(productId);
        if (product != null) {
            product.setStock(newStockStatus);
            productRepo.save(product);
        }
    }

    public void updateProductQuantity(Long productId, int newQuantity) {
        Product product = productRepo.getProductById(productId);
        if (product != null) {
            product.setQuantity(newQuantity);
            productRepo.save(product);
        }
    }

    public void updateProductPrice(Long productId, double newPrice) {
        Product product = productRepo.getProductById(productId);
        if (product != null) {
            product.setPrice(newPrice);
            productRepo.save(product);
        }
    }

    public void deleteProductById(Long id) {
        productRepo.deleteById(id);
        System.out.println("Product with id "
                + id
                + " successfully deleted!" );
    }

}
