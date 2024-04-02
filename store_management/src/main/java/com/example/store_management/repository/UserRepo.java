package com.example.store_management.repository;

import com.example.store_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User create(User user);

    Optional<User> findByUserName(String userName);

    void deleteByUserName(String userName);

}
