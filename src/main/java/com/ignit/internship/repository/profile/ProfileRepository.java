package com.ignit.internship.repository.profile;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.profile.UserProfile;

@Repository
public interface ProfileRepository extends CrudRepository<UserProfile, Long> {
    Optional<UserProfile> findByUsername(String username);
    Optional<UserProfile> findByEmail(String email);    
}
