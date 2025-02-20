package com.ignit.internship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.JwtTokenResponse;
import com.ignit.internship.dto.UserLoginRequest;
import com.ignit.internship.dto.UserRegisterRequest;
import com.ignit.internship.model.UserPublic;
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
    public ResponseEntity<DefaultResponse<UserPublic>> register(@RequestBody @Valid UserRegisterRequest register) {
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
