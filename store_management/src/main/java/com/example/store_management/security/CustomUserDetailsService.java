package  com.example.store_management.security;

import com.example.store_management.entity.Employee;
import com.example.store_management.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.store_management.security.SpringSecurity.passwordEncoder;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepo employeeRepo;

    public CustomUserDetailsService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public EmployeeDetailsAdaptor loadUserByUsername(String userName) throws UsernameNotFoundException {
        Employee employee = employeeRepo.findByUserName(userName);

        if (employee != null) {
            return new EmployeeDetailsAdaptor(employee);
        } else {
            throw new UsernameNotFoundException("Wrong username or password.");
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

//    @Bean
//    static GrantedAuthorityDefaults grantedAuthorityDefaults() {
//        return new GrantedAuthorityDefaults("USER");
//    }
}