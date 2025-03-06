package com.ignit.internship.dto.temukarier;

import java.util.List;

import com.ignit.internship.dto.utils.ImageRequest;

public class MagangRequest {

    private String name;

    private String description;

    private String url;

    private String imageName;

    private String imageType;

    private String imageData;

    private ImageRequest imageRequest;

    private List<String> tags;

    public MagangRequest(
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
        this.imageName = imageName;
        this.imageType = imageType;
        this.imageData = imageData;
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

    public String getImageName() {
        return imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public String getImageData() {
        return imageData;
    }

    public ImageRequest getImageRequest() {
        return imageRequest;
    }

    public List<String> getTags() {
        return tags;
    }
}
