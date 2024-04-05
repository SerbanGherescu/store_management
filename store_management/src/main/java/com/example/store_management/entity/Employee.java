package com.example.store_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    private String role;

    // Constructor
    public Employee(String firstName, String lastName, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.userName = firstName + lastName;
    }

    // Setters for firstName and lastName
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        this.userName = firstName + lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.userName = firstName + lastName;
    }

}
