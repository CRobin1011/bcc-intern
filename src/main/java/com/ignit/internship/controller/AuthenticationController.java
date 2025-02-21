package com.ignit.internship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.auth.JwtTokenResponse;
import com.ignit.internship.dto.auth.UserLoginRequest;
import com.ignit.internship.dto.auth.UserRegisterRequest;
import com.ignit.internship.model.UserProfile;
import com.ignit.internship.service.AuthenticationService;
import com.ignit.internship.service.JwtTokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    JwtTokenService jwtTokenService;

    @PostMapping("/register")
    public ResponseEntity<DefaultResponse<UserProfile>> register(@RequestBody @Valid UserRegisterRequest register) {
        return ResponseEntity.ok().body(DefaultResponse.success(authenticationService.register(register)));
    }

    @PostMapping("/login")
    public ResponseEntity<DefaultResponse<JwtTokenResponse>> login(@RequestBody UserLoginRequest login) {
        String token = jwtTokenService.buildToken(authenticationService.authenticate(login));
        return ResponseEntity.ok().body(DefaultResponse.success(
            new JwtTokenResponse(token, jwtTokenService.getExpirationTime())
        ));
    }
}
