package com.ignit.internship.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class UserThread {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UserComment> comments;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private UserProfile profile;

    @JsonProperty
    private Long createdBy() {
        return profile.getId();
    }

    @SuppressWarnings("unused")
    private UserThread() {}

    public UserThread(String title, String content, UserProfile profile) {
        this.title = title;
        this.content = content;
        this.comments = new ArrayList<UserComment>();
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<UserComment> getComments() {
        return comments;
    }

    public void addComments(UserComment comment) {
        this.comments.add(comment);
    }
}
