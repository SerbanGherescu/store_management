package com.example.store_management.controller;

import com.example.store_management.entity.Employee;
import com.example.store_management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/adminPanel")
public class AdminPanelController {

    private final EmployeeService employeeService;

    @Autowired
    public AdminPanelController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String adminPanelPage(Model model) {
        List<Employee> employees = employeeService.showAllEmployees();
        model.addAttribute("employees", employees);
        return "adminPanel";
    }



    @GetMapping("/createNewEmployee")
    public String createEmployeeForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);

        return "createNewEmployee";
    }


    @PostMapping("/createNewEmployee")
    public String createNewEmployee(@ModelAttribute("employee") Employee employee,
                                    RedirectAttributes redirectAttributes) {
        Employee existingEmployee = employeeService.findEmployeeByUserName(employee.getUserName());
        if (existingEmployee != null) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Employee with username "
                    + employee.getUserName()
                    + " already exists!");
            return "redirect:/adminPanel/createNewEmployee";
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(employee.getPassword());
            employee.setRole("EMPLOYEE");
            employee.setPassword(encodedPassword);
            employeeService.saveEmployee(employee);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Employee created successfully!");
            return "redirect:/adminPanel";
        }
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        // Checking if users has necessary role
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        employeeService.deleteEmployeeById(id);

        return ResponseEntity.noContent().build();
    }


}
