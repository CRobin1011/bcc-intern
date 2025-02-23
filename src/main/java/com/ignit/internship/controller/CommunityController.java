package com.ignit.internship.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.community.ThreadCommentRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.auth.User;
import com.ignit.internship.model.community.UserThread;
import com.ignit.internship.service.community.CommunityService;

@RestController
@RequestMapping("/api/communities")
public class CommunityController {

    private final CommunityService communityService;

    public CommunityController(CommunityService threadCommentService) {
        this.communityService = threadCommentService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<DefaultResponse<UserThread>> postThread(
        @PathVariable Long id,
        @RequestBody ThreadCommentRequest request,
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        UserThread thread = communityService.postThread(request, id, user.getId());
        return ResponseEntity.created(URI.create("/thread/" + user.getId() + "/")).body(DefaultResponse.success(thread));
    }
}
