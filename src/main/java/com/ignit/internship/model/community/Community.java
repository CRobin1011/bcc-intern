package com.ignit.internship.model.community;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UserThread> threads;

    public Community(String name, String description) {
        this.name = name;
        this.description = description;
        this.threads = new ArrayList<UserThread>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserThread> getThreads() {
        return threads;
    }

    public void addThread(UserThread thread) {
        this.threads.add(thread);
    }
}
