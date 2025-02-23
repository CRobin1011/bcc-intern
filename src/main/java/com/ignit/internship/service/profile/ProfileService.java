package com.ignit.internship.service.profile;

import org.springframework.stereotype.Service;

import com.ignit.internship.dto.profile.ProfileUpdateRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.profile.UserProfile;
import com.ignit.internship.repository.profile.ProfileRepository;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(final ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public UserProfile getProfile(Long id) throws IdNotFoundException {
        return profileRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Profile not found"));
    }

    public UserProfile updateProfile(ProfileUpdateRequest request, Long id) throws IdNotFoundException {
        UserProfile profile = profileRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Profile not found"));
        if (request.getFullName() != null) profile.setFullName(request.getFullName());
        if (request.getDescription() != null) profile.setDescription(request.getDescription());
        return profileRepository.save(profile);
    }
}
