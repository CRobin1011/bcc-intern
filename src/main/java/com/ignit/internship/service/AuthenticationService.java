package com.ignit.internship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ignit.internship.dto.UserLoginRequest;
import com.ignit.internship.dto.UserRegisterRequest;
import com.ignit.internship.model.User;
import com.ignit.internship.model.UserPublic;
import com.ignit.internship.repository.UserRepository;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserPublic register(UserRegisterRequest register) {
        User registeredUser = userRepository.save(new User(
            register.getUsername(),
            passwordEncoder.encode(register.getPassword()),
            register.getEmail(),
            new SimpleGrantedAuthority("ROLE_USER")
        ));

        return registeredUser.getUserPublic();
    }

    public User authenticate(UserLoginRequest login) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                login.getUsername(),
                login.getPassword()
            )
        );

        return userRepository.findByUsername(login.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
