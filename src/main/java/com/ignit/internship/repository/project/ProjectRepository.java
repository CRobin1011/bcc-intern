package com.ignit.internship.repository.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.project.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {}
