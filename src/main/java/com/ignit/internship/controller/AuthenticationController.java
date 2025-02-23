package com.ignit.internship.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.auth.JwtTokenResponse;
import com.ignit.internship.dto.auth.UserLoginRequest;
import com.ignit.internship.dto.auth.UserRegisterRequest;
import com.ignit.internship.model.profile.UserProfile;
import com.ignit.internship.service.auth.AuthenticationService;
import com.ignit.internship.service.auth.JwtTokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;

    private final JwtTokenService jwtTokenService;

    public AuthenticationController(final AuthenticationService authenticationService, final JwtTokenService jwtTokenService) {
        this.authenticationService = authenticationService;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<DefaultResponse<UserProfile>> register(@RequestBody @Valid UserRegisterRequest register) {
        return ResponseEntity.ok().body(DefaultResponse.success(authenticationService.register(register)));
    }

    @PostMapping("/login")
    public ResponseEntity<DefaultResponse<JwtTokenResponse>> login(@RequestBody UserLoginRequest login) {
        String token = jwtTokenService.buildToken(authenticationService.authenticate(login));
        return ResponseEntity.ok().body(DefaultResponse.success(new JwtTokenResponse(token)));
    }
}
