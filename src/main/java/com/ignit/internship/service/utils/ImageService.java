package com.ignit.internship.service.utils;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.ignit.internship.model.utils.Image;
import com.ignit.internship.repository.utils.ImageRepository;

import io.jsonwebtoken.io.Decoders;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image uploadImage(String name, String type, String file) throws IOException {
        return imageRepository.save(
            new Image(name, type, Decoders.BASE64.decode(file))
        );
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
