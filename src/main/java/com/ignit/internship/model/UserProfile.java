package com.ignit.internship.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserThread> userThreads;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserComment> userComments;

    @SuppressWarnings("unused")
    private UserProfile() {}

    public UserProfile(User user) {
        this.user = user;
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.userThreads = new ArrayList<UserThread>();
        this.userComments = new ArrayList<UserComment>();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
