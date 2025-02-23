package com.ignit.internship.service.auth;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ignit.internship.dto.auth.UserLoginRequest;
import com.ignit.internship.dto.auth.UserRegisterRequest;
import com.ignit.internship.model.auth.User;
import com.ignit.internship.model.profile.UserProfile;
import com.ignit.internship.repository.auth.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationService(
        final UserRepository userRepository, 
        final BCryptPasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserProfile register(UserRegisterRequest register) {
        User registeredUser = userRepository.save(new User(
            register.getUsername(),
            passwordEncoder.encode(register.getPassword()),
            register.getEmail(),
            new SimpleGrantedAuthority("ROLE_USER")
        ));

        return registeredUser.getProfile();
    }

    public User authenticate(UserLoginRequest login) {
        User user = userRepository.findByUsername(login.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Password not match");
        }

        return user;
    }
}
