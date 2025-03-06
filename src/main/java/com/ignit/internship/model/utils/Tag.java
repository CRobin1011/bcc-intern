package com.ignit.internship.model.utils;

import java.util.ArrayList;
import java.util.List;

import com.ignit.internship.model.cakrawala.Info;
import com.ignit.internship.model.temukarier.Project;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
public class Tag {

    @Id
    private String name;

    @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL)
    private List<Project> projects;

    @OneToOne(mappedBy = "tag", cascade = CascadeType.ALL, optional = true)
    @PrimaryKeyJoinColumn
    private Info info;

    @SuppressWarnings("unused")
    private Tag() {}

    public Tag(String name) {
        this.name = name;
        this.projects = new ArrayList<Project>();
    }

    public String getName() {
        return name;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
