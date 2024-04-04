package com.example.store_management.service;

import com.example.store_management.entity.User;
import com.example.store_management.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {

        this.userRepo = userRepo;

    }

    public void saveUser(User user) {

        User savedUser = userRepo.create(user);

        System.out.println("User "
                + user.getUserName()
                + " successfully created!");

    }

    public User findUserByName(String name) {

        User userFound = userRepo.findByUserName(name);

        return userFound;

    }

    public List<User> showAllUsers(){
        List<User> allUsersFound = userRepo.findAll();
        return allUsersFound;
    }

    public void deleteUserByUserName(String name) {

        userRepo.deleteByUserName(name);

        System.out.println("User "
                + name
                + " successfully deleted!");

    }

}
