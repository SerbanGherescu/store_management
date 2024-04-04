package com.example.store_management.security;

import com.example.store_management.entity.Employee;
import com.example.store_management.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

@Service
public class CustomEmployeeDetailsService implements UserDetailsService {

    private final EmployeeRepo employeeRepo;

    @Autowired
    public CustomEmployeeDetailsService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests((requests) ->
                requests.requestMatchers(HttpMethod.POST, "/category/createNewCategory"
                                            ,"/product/createNewProduct"
                                            ,"/adminPanel/createNewEmployee","/adminPanel")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/listOfCategories"
                                            ,"listOfProducts","/index")
                        .authenticated()
                        .requestMatchers("/login")
                        .permitAll())
                .formLogin(
                        form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/index")
                        .permitAll())
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );

                return http.build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepo.findByUserName(username);

        if (employee != null) {
            return new EmployeeDetailsAdaptor(employee);
        }else{
            try {
                throw new EmployeeNotFoundException("This account does not exist!");
            } catch (EmployeeNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN");

    }
    @Bean
    static GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("EMPLOYEE");
    }

}
