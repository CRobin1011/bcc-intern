package com.ignit.internship.service.cakrawala;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ignit.internship.dto.cakrawala.InfoRequest;
import com.ignit.internship.dto.utils.ImageRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.cakrawala.Info;
import com.ignit.internship.model.utils.Image;
import com.ignit.internship.repository.cakrawala.InfoRepository;
import com.ignit.internship.repository.utils.TagRepository;
import com.ignit.internship.service.utils.ImageService;

@Service
public class InfoService {

    private final InfoRepository infoRepository;

    private final TagRepository tagRepository;

    private final ImageService imageService;

    public InfoService(
        final InfoRepository infoRepository, 
        final TagRepository tagRepository,
        final ImageService imageService
    ) {
        this.infoRepository = infoRepository;
        this.tagRepository = tagRepository;
        this.imageService = imageService;
    }

    public Info getInfo(String name) throws IdNotFoundException {
        return infoRepository.findById(name).orElseThrow(() -> new IdNotFoundException("Info not found"));
    }

    public Info createInfo(InfoRequest request) throws IdNotFoundException, IOException {
        if (!tagRepository.existsById(request.getTag())) {
            throw new IdNotFoundException("Tag not found");
        }       

        List<Long> imageIds = new ArrayList<Long>();
        for (ImageRequest imageRequest : request.getImagesRequests()) {
            Image image = imageService.uploadImage(imageRequest);
            imageIds.add(image.getId());
        }

        Info info = new Info(
            request.getTag(),
            request.getDescription(),
            request.getSalaryRange(),
            request.getCriteria(),
            request.getSkills(),
            request.getRelatedStudies(),
            request.getCareerOpportunities(),
            request.getResponsibilites(),
            request.getQuestions(),
            request.getAnswer(),
            tagRepository.findById(request.getTag()).orElseThrow(() -> new IdNotFoundException("Tag not found")),
            imageIds
        );

        return infoRepository.save(info);
    }
}
