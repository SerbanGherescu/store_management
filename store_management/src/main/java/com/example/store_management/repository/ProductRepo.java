package com.example.store_management.repository;

import com.example.store_management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Product save(Product product);

    Product findByName(String name);

    List<Product> findByCategoryId(Long id);

    void deleteByName(String name);

    void deleteById(Long id);

    Product getProductById(Long idd);
}
