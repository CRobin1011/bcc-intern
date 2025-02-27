package com.ignit.internship.service.utils;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ignit.internship.model.utils.Image;
import com.ignit.internship.repository.utils.ImageRepository;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image uploadImage(MultipartFile file) throws IOException {
        return imageRepository.save(new Image(file.getBytes()));
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
