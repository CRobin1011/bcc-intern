package com.ignit.internship.model.project;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ignit.internship.enums.ProjectStatus;
import com.ignit.internship.model.utils.Tag;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "text")
    private String decription;

    private Long imageId;

    private ProjectStatus status;

    private LocalDateTime deadline;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Tag> tags;

    @JsonProperty
    private List<String> tags() {
        List<String> tagName = new ArrayList<String>();
        for (Tag tag : tags) {
            tagName.add(tag.getName());
        }
        return tagName;
    }

    @SuppressWarnings("unused")
    private Project() {}

    public Project(
        String name, 
        String decription,
        Long imageId,
        ProjectStatus status, 
        LocalDateTime deadline, 
        List<Tag> tags
    ) {
        this.name = name;
        this.decription = decription;
        this.imageId = imageId;
        this.status = status;
        this.deadline = deadline;
        this.tags = tags;
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

    public String getDecription() {
        return decription;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
