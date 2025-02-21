package com.ignit.internship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ignit.internship.dto.thread.ThreadCommentRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.UserComment;
import com.ignit.internship.model.UserProfile;
import com.ignit.internship.model.UserThread;
import com.ignit.internship.repository.CommentRepository;
import com.ignit.internship.repository.ProfileRepository;
import com.ignit.internship.repository.ThreadRepository;

@Service
public class ThreadCommentService {

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public UserThread postThread(ThreadCommentRequest request, Long id) {
        UserProfile profile = profileRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Profile not found"));
        UserThread thread = new UserThread(request.getTitle(), request.getContent(), profile);
        profile.addUserThreads(thread);
        return threadRepository.save(thread);
    }

    public UserComment postComment(ThreadCommentRequest request, Long profileId, Long threadId) {
        UserProfile profile = profileRepository.findById(profileId).orElseThrow(() -> new IdNotFoundException("Profile not found"));
        UserThread thread = threadRepository.findById(threadId).orElseThrow(() -> new IdNotFoundException("Thread not found"));
        UserComment comment = new UserComment(request.getContent(), thread, profile);
        thread.addComments(comment);
        profile.addUserComments(comment);
        return commentRepository.save(comment);
    }

    public UserComment postReply(ThreadCommentRequest request, Long profileId, Long threadId, Long commentId) {
        UserProfile profile = profileRepository.findById(profileId).orElseThrow(() -> new IdNotFoundException("Profile not found"));
        UserThread thread = threadRepository.findById(threadId).orElseThrow(() -> new IdNotFoundException("Thread not found"));
        UserComment parentComment = commentRepository.findById(commentId).orElseThrow(() -> new IdNotFoundException("Comment not found"));
        UserComment comment = new UserComment(request.getContent(), thread, parentComment, profile);
        thread.addComments(comment);
        profile.addUserComments(comment);
        return commentRepository.save(comment);
    }
}
