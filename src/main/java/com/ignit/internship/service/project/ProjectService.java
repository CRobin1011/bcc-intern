package com.ignit.internship.service.project;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.ignit.internship.dto.project.ProjectRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.project.Project;
import com.ignit.internship.model.utils.Image;
import com.ignit.internship.repository.project.ProjectRepository;
import com.ignit.internship.repository.utils.TagRepository;
import com.ignit.internship.service.utils.ImageService;

import jakarta.transaction.Transactional;

@Service
public final class ProjectService {

    private final ProjectRepository projectRepository;

    private final TagRepository tagRepository;

    private final ImageService imageService;

    public ProjectService(
        final ProjectRepository projectRepository, 
        final TagRepository tagRepository, 
        final ImageService imageService
    ) {
        this.projectRepository = projectRepository;
        this.tagRepository = tagRepository;
        this.imageService = imageService;
    }

    @Transactional
    public Project createProject(ProjectRequest request) throws IdNotFoundException, IOException {
        if (!tagRepository.existsAllById(request.getTags())) {
            throw new IdNotFoundException("Tag not found");
        }

        Image image = imageService.uploadImage(request.getFile());

        Project project = new Project(
            request.getName(),
            request.getDescription(),
            image.getId(),
            request.getStatus(),
            request.getDeadline(),
            tagRepository.findAllById(request.getTags())
        );

        return projectRepository.save(project);
    }

    public Project updateProject(Long id, ProjectRequest request) throws IdNotFoundException, IOException {
        Project project = projectRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Project not found"));

        if (request.getName() != null) project.setName(request.getName());
        if (request.getDescription() != null) project.setDecription(request.getDescription());
        if (request.getFile() != null) {
            imageService.deleteImage(project.getId());
            Image image = imageService.uploadImage(request.getFile());
            project.setImageId(image.getId());
        }
        if (request.getDeadline() != null) project.setStatus(request.getStatus());
        if (request.getStatus() != null) project.setDeadline(request.getDeadline());
        if (request.getTags() != null) project.setTags(tagRepository.findAllById(request.getTags()));

        return projectRepository.save(project);
    }
}
