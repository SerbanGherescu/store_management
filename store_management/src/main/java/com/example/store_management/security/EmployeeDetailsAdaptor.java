package com.example.store_management.security;

import com.example.store_management.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class EmployeeDetailsAdaptor implements UserDetails {

    private final Employee employee;

    @Autowired
    public EmployeeDetailsAdaptor(Employee employee) {
        this.employee = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = employee.getRole();
        if (role != null && !role.isEmpty()) {
            return Collections.singleton(new SimpleGrantedAuthority(role));
        } else {
            // If role is null or empty, return an empty collection
            return Collections.emptyList();
        }
    }

    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    @Override
    public String getUsername() {
        return employee.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
