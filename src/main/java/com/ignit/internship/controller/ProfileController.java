package com.ignit.internship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.profile.ProfileUpdateRequest;
import com.ignit.internship.model.UserProfile;
import com.ignit.internship.model.User;
import com.ignit.internship.service.ProfileService;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @GetMapping
    public ResponseEntity<DefaultResponse<UserProfile>> getCurrentProfile(
        @CurrentSecurityContext SecurityContext context
    ) {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(DefaultResponse.success(profileService.getProfile(user.getId())));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<DefaultResponse<UserProfile>> getProfileById(@PathVariable Long id) {
        return ResponseEntity.ok().body(DefaultResponse.success(profileService.getProfile(id)));
    }

    @PutMapping
    public ResponseEntity<DefaultResponse<UserProfile>> updateProfile(
        ProfileUpdateRequest request,
        @CurrentSecurityContext SecurityContext context
    ) {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(DefaultResponse.success(profileService.updateProfile(request, user.getId())));
    }
}
