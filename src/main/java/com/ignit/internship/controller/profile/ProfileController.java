package com.ignit.internship.controller.profile;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.profile.SkillRequest;
import com.ignit.internship.dto.profile.EducationRequest;
import com.ignit.internship.dto.profile.ProfileUpdateRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.auth.User;
import com.ignit.internship.model.profile.UserProfile;
import com.ignit.internship.service.profile.ProfileService;

@RestController
@RequestMapping("/api/profiles")
public final class ProfileController {

    private final ProfileService profileService;

    public ProfileController(final ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/me")
    public ResponseEntity<DefaultResponse<UserProfile>> getCurrentProfile(
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(DefaultResponse.success(profileService.getProfile(user.getId())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse<UserProfile>> getProfileById(@PathVariable Long id) throws IdNotFoundException {
        return ResponseEntity.ok().body(DefaultResponse.success(profileService.getProfile(id)));
    }

    @PatchMapping("/me")
    public ResponseEntity<DefaultResponse<UserProfile>> updateProfile(
        @RequestBody ProfileUpdateRequest request,
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(DefaultResponse.success(profileService.updateProfile(request, user.getId())));
    }

    @PatchMapping("/me/skills")
    public ResponseEntity<DefaultResponse<UserProfile>> addSkill(
        @RequestBody SkillRequest request,
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(DefaultResponse.success(profileService.addSkill(request, user.getId())));
    }

    @PatchMapping("/me/skills/{id}")
    public ResponseEntity<DefaultResponse<UserProfile>> removeSkill(
        @PathVariable Long id,
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(DefaultResponse.success(profileService.removeSkill(id, user.getId())));
    }

    @PatchMapping("/me/educations")
    public ResponseEntity<DefaultResponse<UserProfile>> addEducation(
        @RequestBody EducationRequest request,
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(DefaultResponse.success(profileService.addEducation(request, user.getId())));
    }

    @PatchMapping("/me/education/{id}")
    public ResponseEntity<DefaultResponse<UserProfile>> removeEducation(
        @PathVariable Long id,
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(DefaultResponse.success(profileService.removeEducation(id, user.getId())));
    }
}
