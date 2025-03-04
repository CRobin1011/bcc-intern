package com.ignit.internship.controller.temukarier;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.temukarier.ProjectRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.auth.User;
import com.ignit.internship.model.temukarier.Project;
import com.ignit.internship.service.temukarier.ProjectService;

@RestController
@RequestMapping("/api/temukarier/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<DefaultResponse<Project>> createProject(
        @RequestBody ProjectRequest request,
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException, IOException {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(DefaultResponse.success(projectService.createProject(request, user.getId())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse<Project>> getProject(@PathVariable Long id) throws IdNotFoundException {
        return ResponseEntity.ok().body(DefaultResponse.success(projectService.getProjectById(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DefaultResponse<Project>> updateProject(
        @RequestBody ProjectRequest request,
        @PathVariable Long id,
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException, IOException {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(DefaultResponse.success(projectService.updateProject(request, id, user.getId())));
    }

    @GetMapping
    public ResponseEntity<DefaultResponse<Page<Project>>> getProjectByTagsAndPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(required = false) String tag,
        @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        if (tag == null) {
            return ResponseEntity.ok().body(DefaultResponse.success(projectService.getProjectByPage(pageable)));
        }
        else {
            return ResponseEntity.ok().body(DefaultResponse.success(projectService.getProjectByPageAndTag(pageable, tag)));
        }
    }
}
