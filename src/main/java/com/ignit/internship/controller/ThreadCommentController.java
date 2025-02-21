package com.ignit.internship.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.thread.ThreadCommentRequest;
import com.ignit.internship.model.User;
import com.ignit.internship.model.UserThread;
import com.ignit.internship.service.ThreadCommentService;

@RestController
@RequestMapping("/api/thread")
public class ThreadCommentController {
    
    @Autowired
    ThreadCommentService threadCommentService;

    @PostMapping
    public ResponseEntity<DefaultResponse<UserThread>> postThread(
        ThreadCommentRequest request,
        @CurrentSecurityContext SecurityContext context
    ) {
        User user = (User) context.getAuthentication().getPrincipal();
        UserThread thread = threadCommentService.postThread(request, user.getId());
        return ResponseEntity.created(URI.create("/thread/" + user.getId() + "/")).body(DefaultResponse.success(thread));
    }
}
