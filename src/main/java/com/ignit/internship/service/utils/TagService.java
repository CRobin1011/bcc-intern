package com.ignit.internship.service.utils;

import org.springframework.stereotype.Service;

import com.ignit.internship.model.utils.Tag;
import com.ignit.internship.repository.utils.TagRepository;

import jakarta.transaction.Transactional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(final TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional
    public Tag createTag(String name) {
        return tagRepository.save(new Tag(name));
    }
}
