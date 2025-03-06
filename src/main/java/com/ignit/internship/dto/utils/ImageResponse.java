package com.ignit.internship.dto.utils;

public class ImageResponse {

    private String name;

    private String type;

    private String data;

    public ImageResponse(String name, String type, String data) {
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

