package com.ignit.internship.service.temukarier;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ignit.internship.dto.temukarier.BootcampRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.temukarier.Bootcamp;
import com.ignit.internship.model.utils.Image;
import com.ignit.internship.repository.temukarier.BootcampRepository;
import com.ignit.internship.repository.utils.TagRepository;
import com.ignit.internship.service.utils.ImageService;

@Service
public class BootcampService {
    private final BootcampRepository bootcampRepository;

    private final TagRepository tagRepository;

    private final ImageService imageService;

    public BootcampService(
        final BootcampRepository bootcampRepository, 
        final TagRepository tagRepository, 
        final ImageService imageService
    ) {
        this.bootcampRepository = bootcampRepository;
        this.tagRepository = tagRepository;
        this.imageService = imageService;
    }

    public Bootcamp getBootcampById(Long id) throws IdNotFoundException {
        return bootcampRepository.findById(id).orElseThrow(() -> new IdNotFoundException("bootcamp not found"));
    } 

    public Page<Bootcamp> getBootcampByPage(Pageable pageable) {
        return bootcampRepository.findAll(pageable);
    }

    public Page<Bootcamp> getBootcampByPageAndTag(Pageable pageable, String tag) {
        return bootcampRepository.findByTagName(tag, pageable);
    }

    public Bootcamp createBootcamp(BootcampRequest request) throws IdNotFoundException, IOException {
        if (!tagRepository.existsAllById(request.getTags())) {
            throw new IdNotFoundException("Tag not found");
        }

        Image image = imageService.uploadImage(request.getImageRequest());

        return bootcampRepository.save(new Bootcamp(
            request.getName(), 
            request.getDescription(),
            image.getId(), 
            request.getUrl(),
            tagRepository.findAllById(request.getTags())
        ));
    }

    public Bootcamp updateBootcamp(BootcampRequest request, Long id) throws IdNotFoundException, IOException {
        Bootcamp bootcamp = bootcampRepository.findById(id).orElseThrow(() -> new IdNotFoundException("bootcamp not found"));

        bootcamp.setName(request.getName());
        bootcamp.setDescription(request.getDescription());
        if (request.getImageRequest() != null) {
            imageService.deleteImage(bootcamp.getId());
            Image image = imageService.uploadImage(request.getImageRequest());
            bootcamp.setImageId(image.getId());    
        }
        bootcamp.setUrl(request.getUrl());
        bootcamp.setTags(tagRepository.findAllById(request.getTags()));

        return bootcampRepository.save(bootcamp);
    }
}
