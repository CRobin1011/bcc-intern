package com.ignit.internship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ignit.internship.exception.EmailNotFoundException;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.repository.UserRepository;

@Service
public class IgnitUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDetails loadUserById(Long id) throws IdNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new IdNotFoundException("User not found"));
    }

    public UserDetails loadUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException("User not found"));
    }
}
