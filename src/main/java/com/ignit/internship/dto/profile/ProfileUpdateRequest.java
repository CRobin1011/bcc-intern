package com.ignit.internship.dto.profile;

import java.util.List;

public class ProfileUpdateRequest {

    private String fullName;

    private List<String> passions;

    private String summary;

    public ProfileUpdateRequest(String fullName, List<String> passions, String summary) {
        this.fullName = fullName;
        this.passions = passions;
        this.summary = summary;
    }

    public String getFullName() {
        return fullName;
    }

    public List<String> getPassions() {
        return passions;
    }

    public String getSummary() {
        return summary;
    }
}
