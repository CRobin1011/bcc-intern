package com.ignit.internship.service.community;

import org.springframework.stereotype.Service;

import com.ignit.internship.dto.community.ThreadCommentRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.community.Community;
import com.ignit.internship.model.community.UserComment;
import com.ignit.internship.model.community.UserThread;
import com.ignit.internship.model.profile.UserProfile;
import com.ignit.internship.repository.community.CommentRepository;
import com.ignit.internship.repository.community.CommunityRepository;
import com.ignit.internship.repository.community.ThreadRepository;
import com.ignit.internship.repository.profile.ProfileRepository;

@Service
public class CommunityService {

    private final CommunityRepository communityRepository;

    private final ThreadRepository threadRepository;

    private final CommentRepository commentRepository;

    private final ProfileRepository profileRepository;

    public CommunityService(
        final CommunityRepository communityRepository,
        final ThreadRepository threadRepository, 
        final CommentRepository commentRepository,
        final ProfileRepository profileRepository
    ) {
        this.communityRepository = communityRepository;
        this.threadRepository = threadRepository;
        this.commentRepository = commentRepository;
        this.profileRepository = profileRepository;
    }

    public UserThread postThread(ThreadCommentRequest request, Long communityId, Long profileId) throws IdNotFoundException {
        UserProfile profile = profileRepository.findById(profileId).orElseThrow(() -> new IdNotFoundException("Profile not found"));
        Community community = communityRepository.findById(communityId).orElseThrow(() -> new IdNotFoundException("Community not found"));
        UserThread thread = new UserThread(request.getTitle(), request.getContent(), community, profile);
        profile.addUserThreads(thread);
        return threadRepository.save(thread);
    }

    public UserComment postComment(ThreadCommentRequest request, Long profileId, Long threadId) throws IdNotFoundException {
        UserProfile profile = profileRepository.findById(profileId).orElseThrow(() -> new IdNotFoundException("Profile not found"));
        UserThread thread = threadRepository.findById(threadId).orElseThrow(() -> new IdNotFoundException("Thread not found"));
        UserComment comment = new UserComment(request.getContent(), thread, profile);
        thread.addComments(comment);
        profile.addUserComments(comment);
        return commentRepository.save(comment);
    }

    public UserComment postReply(ThreadCommentRequest request, Long profileId, Long threadId, Long commentId) throws IdNotFoundException {
        UserProfile profile = profileRepository.findById(profileId).orElseThrow(() -> new IdNotFoundException("Profile not found"));
        UserThread thread = threadRepository.findById(threadId).orElseThrow(() -> new IdNotFoundException("Thread not found"));
        UserComment parentComment = commentRepository.findById(commentId).orElseThrow(() -> new IdNotFoundException("Comment not found"));
        UserComment comment = new UserComment(request.getContent(), thread, parentComment, profile);
        thread.addComments(comment);
        profile.addUserComments(comment);
        return commentRepository.save(comment);
    }
}
