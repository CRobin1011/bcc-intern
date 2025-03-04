package com.ignit.internship.service.temukarier;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ignit.internship.dto.temukarier.MagangRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.temukarier.Magang;
import com.ignit.internship.model.utils.Image;
import com.ignit.internship.repository.temukarier.MagangRepository;
import com.ignit.internship.repository.utils.TagRepository;
import com.ignit.internship.service.utils.ImageService;

@Service
public class MagangService {

    private final MagangRepository magangRepository;

    private final TagRepository tagRepository;

    private final ImageService imageService;

    public MagangService(
        final MagangRepository magangRepository, 
        final TagRepository tagRepository, 
        final ImageService imageService
    ) {
        this.magangRepository = magangRepository;
        this.tagRepository = tagRepository;
        this.imageService = imageService;
    }

    public Magang getMagangById(Long id) throws IdNotFoundException {
        return magangRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Magang not found"));
    } 

    public Page<Magang> getMagangByPage(Pageable pageable) {
        return magangRepository.findAll(pageable);
    }

    public Page<Magang> getMagangByPageAndTag(Pageable pageable, String tag) {
        return magangRepository.findByTagName(tag, pageable);
    }

    public Magang createMagang(MagangRequest request) throws IdNotFoundException, IOException {
        if (!tagRepository.existsAllById(request.getTags())) {
            throw new IdNotFoundException("Tag not found");
        }

        Image image = imageService.uploadImage(request.getImageName(), request.getImageType(), request.getImageData());

        return magangRepository.save(new Magang(
            request.getName(), 
            request.getDescription(),
            image.getId(), 
            request.getUrl(),
            tagRepository.findAllById(request.getTags())
        ));
    }

    public Magang updateMagang(MagangRequest request, Long id) throws IdNotFoundException, IOException {
        Magang magang = magangRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Magang not found"));

        if (request.getName() != null) magang.setName(request.getName());
        if (request.getDescription() != null) magang.setDescription(request.getDescription());
        if (request.getImageName() != null && request.getImageType() != null && request.getImageData() != null) {
            imageService.deleteImage(magang.getId());
            Image image = imageService.uploadImage(request.getImageName(), request.getImageType(), request.getImageData());
            magang.setImageId(image.getId());    
        }
        if (request.getUrl() != null) magang.setUrl(request.getUrl());
        if (request.getTags() != null) magang.setTags(tagRepository.findAllById(request.getTags()));

        return magangRepository.save(magang);
    }
}
