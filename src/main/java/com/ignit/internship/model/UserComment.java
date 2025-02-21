package com.ignit.internship.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class UserComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private UserThread thread;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private UserComment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<UserComment> replies;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private UserProfile profile;

    @JsonProperty
    private Long createdBy() {
        return profile.getId();
    }

    @SuppressWarnings("unused")
    private UserComment() {}

    public UserComment(String content, UserThread thread, UserProfile profile) {
        this(content, thread, null, profile);
    }

    public UserComment(String content, UserThread thread, UserComment parent, UserProfile profile) {
        this.content = content;
        this.replies = new ArrayList<UserComment>();
        this.thread = thread;
        this.parent = parent;
        this.profile = profile;
    }

    public Long getId() {
        return id;
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

    public UserThread getThread() {
        return thread;
    }

    public UserComment getParent() {
        return parent;
    }

    public List<UserComment> getReplies() {
        return replies;
    }

    public void addReplies(UserComment comment) {
        this.replies.add(comment);
    }
}
