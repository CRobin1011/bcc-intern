package com.ignit.internship.service.temukarier;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ignit.internship.dto.temukarier.ProjectRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.profile.UserProfile;
import com.ignit.internship.model.temukarier.Project;
import com.ignit.internship.model.utils.Image;
import com.ignit.internship.repository.profile.ProfileRepository;
import com.ignit.internship.repository.temukarier.ProjectRepository;
import com.ignit.internship.repository.utils.TagRepository;
import com.ignit.internship.service.utils.ImageService;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final TagRepository tagRepository;

    private final ImageService imageService;

    private final ProfileRepository profileRepository;

    public ProjectService(
        final ProjectRepository projectRepository, 
        final TagRepository tagRepository, 
        final ImageService imageService,
        final ProfileRepository profileRepository
    ) {
        this.projectRepository = projectRepository;
        this.tagRepository = tagRepository;
        this.imageService = imageService;
        this.profileRepository = profileRepository;
    }

    public Project getProjectById(Long id) throws IdNotFoundException {
        return projectRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Project not found"));
    }

    public Page<Project> getProjectByPage(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    public Page<Project> getProjectByPageAndTag(Pageable pageable, String tag) {
        return projectRepository.findByTagName(tag, pageable);
    }

    public Project createProject(ProjectRequest request, Long id) throws IdNotFoundException, IOException {
        if (!tagRepository.existsAllById(request.getTags())) {
            throw new IdNotFoundException("Tag not found");
        }

        Image image = imageService.uploadImage(request.getImageName(), request.getImageType(), request.getImageData());
        
        UserProfile profile = profileRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Profile not found"));

        Project project = projectRepository.save(new Project(
            request.getName(),
            request.getDescription(),
            image.getId(),
            request.getStatus(),
            request.getDeadline(),
            tagRepository.findAllById(request.getTags()),
            profile
        ));

        profile.addProject(project);

        return project;
    }

    public Project updateProject(ProjectRequest request, Long projectId, Long profileId) throws IdNotFoundException, IOException {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IdNotFoundException("Project not found"));

        if (project.getProfile().getId() != profileId) throw new IdNotFoundException("User can only update their own project");

        if (request.getName() != null) project.setName(request.getName());
        if (request.getDescription() != null) project.setDescription(request.getDescription());
        if (request.getImageName() != null && request.getImageType() != null && request.getImageData() != null) {
            imageService.deleteImage(project.getId());
            Image image = imageService.uploadImage(request.getImageName(), request.getImageType(), request.getImageData());
            project.setImageId(image.getId());
        }
        if (request.getDeadline() != null) project.setStatus(request.getStatus());
        if (request.getStatus() != null) project.setDeadline(request.getDeadline());
        if (request.getTags() != null) project.setTags(tagRepository.findAllById(request.getTags()));

        return projectRepository.save(project);
    }
}
