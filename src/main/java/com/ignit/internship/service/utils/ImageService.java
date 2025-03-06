package com.ignit.internship.service.utils;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.ignit.internship.dto.utils.ImageRequest;
import com.ignit.internship.dto.utils.ImageResponse;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.utils.Image;
import com.ignit.internship.repository.utils.ImageRepository;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image uploadImage(ImageRequest request) throws IOException {
        return imageRepository.save(
            new Image(
                request.getName(), 
                request.getType(), 
                Decoders.BASE64.decode(request.getData())
            )
        );
    }

    public ImageResponse getImage(Long id) throws IdNotFoundException {
        Image image = imageRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Image not found"));
        return new ImageResponse(image.getName(), image.getType(), Encoders.BASE64.encode(image.getData()));
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
