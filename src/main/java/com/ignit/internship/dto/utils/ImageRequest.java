package com.ignit.internship.dto.utils;

public class ImageRequest {

    private String name;

    private String type;

    private String data;

    public ImageRequest(String name, String type, String data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    
}
