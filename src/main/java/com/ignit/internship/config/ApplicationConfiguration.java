package com.ignit.internship.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ignit.internship.model.User;
import com.ignit.internship.repository.UserRepository;
import com.ignit.internship.service.ImplUserDetailsService;

@Configuration
public class ApplicationConfiguration {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImplUserDetailsService userDetailsService;

    @Bean
    UserDetailsService userDetailsService() throws Exception {
        return userDetailsService;
    }
    
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    CommandLineRunner createDefaultAdmin() {
        return _ -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User(
                    "admin", 
                    passwordEncoder().encode("admin"),
                    "admin@admin",
                    new SimpleGrantedAuthority("ROLE_USER"),
                    new SimpleGrantedAuthority("ROLE_ADMIN")
                );
                userRepository.save(admin);
            }
        };
    }
}
