package com.example.store_management.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) ->
                        requests.requestMatchers(
                                        "/product/createNewProduct",
                                        "/product/deleteProduct/{id}",
                                        "/category/createNewCategory",
                                        "/category/deleteCategory/{name}",
                                        "/adminPanel",
                                        "/adminPanel/createNewEmployee",
                                        "/adminPanel/deleteEmployee/{id}"
                                        )
                                .hasRole("ADMIN")
                                .requestMatchers("/index",
                                        "/listOfCategories",
                                        "/listOfProducts")
                                .authenticated()
                                .requestMatchers("/login")
                                .permitAll()
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .defaultSuccessUrl("/index")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                ).csrf(csrf -> csrf
                        .ignoringRequestMatchers("/adminPanel/deleteEmployee/**")
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

}
