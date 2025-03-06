package com.ignit.internship.controller.utils;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.utils.ImageRequest;
import com.ignit.internship.dto.utils.ImageResponse;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.utils.Image;
import com.ignit.internship.service.utils.ImageService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(final ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<DefaultResponse<?>> uploadImage(@RequestBody ImageRequest request) throws IOException {
        Image image = imageService.uploadImage(request);
        return ResponseEntity.created(URI.create("/image/" + image.getId())).body(DefaultResponse.success(null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse<ImageResponse>> getImage(@PathVariable Long id) throws IdNotFoundException {
        return ResponseEntity.ok().body(DefaultResponse.success(imageService.getImage(id)));
    }

    @DeleteMapping("/(id)")
    public ResponseEntity<DefaultResponse<?>> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.ok().body(DefaultResponse.success(null));
    }
}
