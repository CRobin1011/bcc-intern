package com.ignit.internship.dto.temukarier;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ignit.internship.enums.ProjectStatus;

public class ProjectRequest {

    private String name;

    private String description;

    private String imageName;

    private String imageType;

    private String imageData;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime deadline;

    private ProjectStatus status;

    private List<String> tags;

    public ProjectRequest(
        String name, 
        String description, 
        String imageName,
        String imageType,        
        String imageData,
        LocalDateTime deadline, 
        ProjectStatus status, 
        List<String> tags
    ) {
        this.name = name;
        this.description = description;
        this.imageName = imageName;
        this.imageType = imageType;
        this.imageData = imageData;
        this.deadline = deadline;
        this.status = status;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public String getImageData() {
        return imageData;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "ProjectRequest{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", imageName='" + imageName + '\'' +
        ", imageType='" + imageType + '\'' +
        ", imageData='" + imageData + '\'' +
        ", deadline=" + deadline +
        ", status=" + status +
        ", tags=" + tags +
        '}';
    }
}
