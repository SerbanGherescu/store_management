package com.example.store_management.service;

import com.example.store_management.entity.Employee;
import com.example.store_management.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {

        this.employeeRepo = employeeRepo;

    }

    public void saveEmployee(Employee employee) {

        Employee savedEmployee = employeeRepo.create(employee);

        System.out.println("Employee "
                + employee.getUserName()
                + " successfully created!");

    }

    public Employee findEmployeeByUserName(String name) {

        Employee employeeFound = employeeRepo.findByUserName(name);

        return employeeFound;

    }

    public List<Employee> showAllEmployees(){
        List<Employee> allEmployeesFound = employeeRepo.findAll();
        return allEmployeesFound;
    }

    public void deleteEmployeeByUserName(String name) {

        employeeRepo.deleteByUserName(name);

        System.out.println("Employee with "
                + name
                + " successfully deleted!");

    }

}
