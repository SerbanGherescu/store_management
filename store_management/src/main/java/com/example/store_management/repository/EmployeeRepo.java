package com.example.store_management.repository;

import com.example.store_management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    Employee findByUserName(String userName);

}
