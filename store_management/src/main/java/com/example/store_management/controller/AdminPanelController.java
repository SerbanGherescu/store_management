package com.example.store_management.controller;

import com.example.store_management.entity.Employee;
import com.example.store_management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/adminPanel")
public class AdminPanelController {

    private final EmployeeService employeeService;

    @Autowired
    public AdminPanelController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String adminPanelPage() {
        return "adminPanel";
    }

//    @GetMapping
//    public ModelAndView showAllEmployees() {
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("employee", employeeService.showAllEmployees());
//
//        return mav;
//    }

    @GetMapping("/createNewEmployee")
    public String createEmployeeForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);

        return "createNewEmployee";
    }


    @PostMapping("/createNewEmployee")
    public String createNewEmployee(@Validated @ModelAttribute("employee")
                                    Employee employee,
                                    BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("Some error appear when trying to create a new employee! Try again!");
            return "createNewEmployee";
        } else {
            Employee existingEmployee = employeeService.findEmployeeByUserName(employee.getUserName());
            if (existingEmployee != null
                    && existingEmployee.getUserName() != null
                    && existingEmployee.getUserName().isEmpty()) {
                result.rejectValue("userName", null, "Employee with userName "
                        + employee.getUserName()
                        + "already exists!");
            } else {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String encodedPassword = encoder.encode(employee.getPassword());
                employee.setPassword(encodedPassword);
                employeeService.saveEmployee(employee);
            }
            return "redirect:/createNewEmployee?success";
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String name) {
        employeeService.deleteEmployeeByUserName(name);
        return ResponseEntity.ok().build();
    }
}
