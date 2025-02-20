package com.ignit.internship.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
public class UserPublic {

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String fullName;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JsonBackReference
    private User user;

    @SuppressWarnings("unused")
    private UserPublic() {}

    public UserPublic(User user) {
        this(null, user);
    }

    public UserPublic(String fullName, User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.fullName = fullName;
        this.user = user;
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

}
