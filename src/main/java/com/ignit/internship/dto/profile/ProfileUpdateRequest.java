package com.ignit.internship.dto.profile;

public class ProfileUpdateRequest {

    private String fullName;

    private String location;

    private String summary;

    public ProfileUpdateRequest(String fullName, String location, String summary) {
        this.fullName = fullName;
        this.location = location;
        this.summary = summary;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLocation() {
        return location;
    }

    public String getSummary() {
        return summary;
    }
}
