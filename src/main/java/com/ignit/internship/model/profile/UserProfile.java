package com.ignit.internship.model.profile;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ignit.internship.model.auth.User;
import com.ignit.internship.model.community.UserComment;
import com.ignit.internship.model.community.UserThread;
import com.ignit.internship.model.temukarier.Project;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class UserProfile {

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String fullName;

    @ElementCollection
    private List<String> passions;

    @Column(columnDefinition = "text")
    private String summary;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Education> educations;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Skill> skills;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserThread> userThreads;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserComment> userComments;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Project> projects;

    @SuppressWarnings("unused")
    private UserProfile() {}

    public UserProfile(User user) {
        this.user = user;
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.passions = new ArrayList<String>();
        this.educations = new ArrayList<Education>();
        this.skills = new ArrayList<Skill>();
        this.userThreads = new ArrayList<UserThread>();
        this.userComments = new ArrayList<UserComment>();
        this.projects = new ArrayList<Project>();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public User getUser() {
        return user;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<String> getPassions() {
        return passions;
    }

    public void setPassions(List<String> passions) {
        this.passions = passions;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void addEducation(Education education) {
        this.educations.add(education);
    }

    public void removeEducation(Long id) {
        this.educations.removeIf((education) -> {
            return education.getId() == id;
        });
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }

    public void removeSkill(Long id) {
        this.skills.removeIf((skill) -> {
            return skill.getId() == id;
        });
    }

    public List<UserThread> getUserThreads() {
        return userThreads;
    }

    public void addUserThreads(UserThread thread) {
        this.userThreads.add(thread);
    }

    public List<UserComment> getUserComments() {
        return userComments;
    }

    public void addUserComments(UserComment comment) {
        this.userComments.add(comment);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }
}
