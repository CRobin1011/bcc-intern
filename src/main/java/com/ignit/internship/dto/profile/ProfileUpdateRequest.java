package com.ignit.internship.dto.profile;

public class ProfileUpdateRequest {

    private String fullName;

    private String description;

    public ProfileUpdateRequest(String fullName, String description) {
        this.fullName = fullName;
        this.description = description;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }
}
