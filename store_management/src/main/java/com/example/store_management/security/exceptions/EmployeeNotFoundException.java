package com.example.store_management.security;

import javax.naming.AuthenticationException;

public class EmployeeNotFoundException extends AuthenticationException {

    public EmployeeNotFoundException(String msg) {
        super(msg);
    }


}
