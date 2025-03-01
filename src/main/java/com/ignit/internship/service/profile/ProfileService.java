package com.ignit.internship.service.profile;

import org.springframework.stereotype.Service;

import com.ignit.internship.dto.profile.SkillRequest;
import com.ignit.internship.dto.profile.EducationRequest;
import com.ignit.internship.dto.profile.ProfileUpdateRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.profile.Education;
import com.ignit.internship.model.profile.Skill;
import com.ignit.internship.model.profile.UserProfile;
import com.ignit.internship.repository.profile.ProfileRepository;

@Service
public final class ProfileService {

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
        if (request.getPassions() != null) profile.setPassions(request.getPassions());
        if (request.getSummary() != null) profile.setSummary(request.getSummary());
        return profileRepository.save(profile);
    }

    public UserProfile addSkill(SkillRequest request, Long id) throws IdNotFoundException {
        UserProfile profile = profileRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Profile not found"));
        profile.addSkill(new Skill(request.getName(), request.getDescription(), request.getYear(), profile));
        return profileRepository.save(profile);
    }

    public UserProfile removeSkill(Long skillId, Long profileId) throws IdNotFoundException {
        UserProfile profile = profileRepository.findById(profileId).orElseThrow(() -> new IdNotFoundException("Profile not found"));
        profile.removeSkill(skillId);
        return profileRepository.save(profile);
    }

    public UserProfile addEducation(EducationRequest request, Long id) throws IdNotFoundException {
        UserProfile profile = profileRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Profile not found"));
        profile.addEducation(new Education(
            request.getDegree(),
            request.getSchool(),
            request.getFieldOfStudy(),
            request.getStartDate(),
            request.getEndDate(),
            profile
        ));
        return profileRepository.save(profile);
    }

    public UserProfile removeEducation(Long educationId, Long profileId) throws IdNotFoundException {
        UserProfile profile = profileRepository.findById(profileId).orElseThrow(() -> new IdNotFoundException("Profile not found"));
        profile.removeEducation(educationId);
        return profileRepository.save(profile);
    }
}
