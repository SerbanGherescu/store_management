package com.example.store_management.repository;

import com.example.store_management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Product create(Product product);

    Product findByName(String name);

    Optional<Product> findById(Long id);

    void deleteByName(String name);

    void deleteById(Long id);



}
