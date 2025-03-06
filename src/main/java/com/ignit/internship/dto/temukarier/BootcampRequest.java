package com.ignit.internship.dto.temukarier;

import java.util.List;

import com.ignit.internship.dto.utils.ImageRequest;

public class BootcampRequest {

    private String name;

    private String description;

    private String url;

    private ImageRequest imageRequest;

    private List<String> tags;

    public BootcampRequest(
        String name, 
        String description, 
        String url,
        String imageName,
        String imageType,        
        String imageData,
        List<String> tags
    ) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
 
    public String getUrl() {
        return url;
    }   

    public ImageRequest getImageRequest() {
        return imageRequest;
    }

    public List<String> getTags() {
        return tags;
    }
}
