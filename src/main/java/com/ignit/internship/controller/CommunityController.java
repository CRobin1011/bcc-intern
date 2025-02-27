package com.ignit.internship.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.community.CommunityRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.auth.User;
import com.ignit.internship.model.community.Community;
import com.ignit.internship.model.community.UserComment;
import com.ignit.internship.model.community.UserThread;
import com.ignit.internship.service.community.CommunityService;

@RestController
@RequestMapping("/api/communities")
public final class CommunityController {

    private final CommunityService communityService;

    public CommunityController(CommunityService threadCommentService) {
        this.communityService = threadCommentService;
    }

    @PostMapping
    public ResponseEntity<DefaultResponse<Community>> createCommunity(@RequestBody CommunityRequest request) {
        Community community = communityService.createCommunity(request);
        return ResponseEntity.created(URI.create("/communities/" + community.getId())).body(DefaultResponse.success(community));
    }

    @GetMapping
    public ResponseEntity<DefaultResponse<List<Community>>> getAllCommunities() {
        return ResponseEntity.ok().body(DefaultResponse.success(communityService.getAllCommunities()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse<Community>> getCommunityById(@PathVariable Long id) throws IdNotFoundException {
        return ResponseEntity.ok().body(DefaultResponse.success(communityService.getCommunity(id)));
    }

    @PostMapping("/{id}/threads")
    public ResponseEntity<DefaultResponse<UserThread>> createThread(
        @PathVariable Long id,
        @RequestBody CommunityRequest request,
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        UserThread thread = communityService.createThread(request, id, user.getId());
        return ResponseEntity.created(
            URI.create("/communities/" + id + "/thread/" + thread.getId() + "/")
        ).body(DefaultResponse.success(thread));
    }

    @GetMapping("/{id}/threads")
    public ResponseEntity<DefaultResponse<List<UserThread>>> getAllThreadsByCommunity(@PathVariable Long id) throws IdNotFoundException {
        return ResponseEntity.ok().body(DefaultResponse.success(communityService.getAllThreadsByCommunity(id)));
    }

    @GetMapping("/{communityId}/threads/{threadId}")
    public ResponseEntity<DefaultResponse<UserThread>> getThreadById(
        @PathVariable Long communityId,
        @PathVariable Long threadId
    ) throws IdNotFoundException {
        return ResponseEntity.ok().body(DefaultResponse.success(communityService.getThread(communityId, threadId)));
    }

    @PostMapping("/{communityId}/threads/{threadId}/comments")
    public ResponseEntity<DefaultResponse<UserComment>> createComment(
        @PathVariable Long communityId,
        @PathVariable Long threadId,
        @RequestBody CommunityRequest request,
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        UserComment comment = communityService.createComment(request, communityId, threadId, user.getId());
        return ResponseEntity.created(
            URI.create("/communities/" + communityId + "/threads/" + threadId + "/comments/" + comment.getId())
        ).body(DefaultResponse.success(comment));
    }

    @GetMapping("/{communityId}/threads/{threadId}/comments")
    public ResponseEntity<DefaultResponse<List<UserComment>>> getAllCommentsByThread(
        @PathVariable Long communityId,
        @PathVariable Long threadId
    ) throws IdNotFoundException {
        return ResponseEntity.ok().body(DefaultResponse.success(communityService.getAllCommentsByThread(communityId, threadId)));
    }

    @GetMapping("/{communityId}/threads/{threadId}/comments/{commentId}")
    public ResponseEntity<DefaultResponse<UserComment>> getCommentById(
        @PathVariable Long communityId,
        @PathVariable Long threadId,
        @PathVariable Long commentId       
    ) throws IdNotFoundException{
        return ResponseEntity.ok().body(DefaultResponse.success(communityService.getComment(communityId, threadId, commentId)));
    }

    @PostMapping("/{communityId}/threads/{threadId}/comments/{commentId}")
    public ResponseEntity<DefaultResponse<UserComment>> createReply(
        @PathVariable Long communityId,
        @PathVariable Long threadId,
        @PathVariable Long commentId,
        @RequestBody CommunityRequest request,
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        UserComment comment = communityService.createReply(request, communityId, threadId, commentId, user.getId());
        return ResponseEntity.created(
            URI.create("/communities/" + communityId + "/threads/" + threadId + "/comments/" + comment.getId())
        ).body(DefaultResponse.success(comment));
    }
}
