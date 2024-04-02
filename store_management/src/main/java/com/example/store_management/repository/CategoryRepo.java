package com.example.store_management.repository;

import com.example.store_management.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    Category create(Category category);

    Optional<Category> findByName(String name);

    void deleteByName(String name);

}
