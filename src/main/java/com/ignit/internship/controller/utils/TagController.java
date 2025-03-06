package com.ignit.internship.controller.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.utils.TagRequest;
import com.ignit.internship.model.utils.Tag;
import com.ignit.internship.service.utils.TagService;

@RestController
@RequestMapping("/api/utils/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<DefaultResponse<Tag>> createTag(@RequestBody TagRequest request) {
        return ResponseEntity.ok().body(DefaultResponse.success(tagService.createTag(request.getName())));
    }
}
