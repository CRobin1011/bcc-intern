package com.ignit.internship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ignit.internship.dto.profile.ProfileUpdateRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.UserProfile;
import com.ignit.internship.repository.ProfileRepository;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public UserProfile getProfile(Long id) {
        return profileRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Profile not found"));
    }

    public UserProfile updateProfile(ProfileUpdateRequest request, Long id) {
        UserProfile profile = profileRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Profile not found"));
        if (request.getFullName() != null) profile.setFullName(request.getFullName());
        if (request.getDescription() != null) profile.setDescription(request.getDescription());
        return profileRepository.save(profile);
    }
}
