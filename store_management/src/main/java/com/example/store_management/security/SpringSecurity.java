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
    private CustomUserDetailsService userDetailsService;

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
                                        "/category/listOfCategories/{id}",
                                        "/category/deleteCategory/{id}",
                                        "/adminPanel",
                                        "/adminPanel/createNewEmployee",
                                        "/adminPanel/deleteEmployee/{id}"
                                )
                                .hasRole("ADMIN")
                                .requestMatchers("/index",
                                        "/category/listOfCategories",
                                        "/product/listOfProducts/{id}",
                                        "/product/updatePrice/{id}",
                                        "/product/updateQuantity/{id}",
                                        "/product/updateStock/{id}")
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
                        .ignoringRequestMatchers("/adminPanel/deleteEmployee/**",
                                "/category/deleteCategory/**",
                                "/product/deleteProduct/**",
                                "/category/listOfCategories/{id}",
                                "/product/createNewProduct",
                                "/product/updatePrice/{id}",
                                "/product/updateQuantity/{id}",
                                "/product/updateStock/{id}",
                                "/favicon.ico","/error")
                );
        return http.build();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        // Add additional in-memory users if needed
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN");
    }

}
