package com.ignit.internship.repository.community;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.community.UserComment;
import com.ignit.internship.model.community.UserThread;

@Repository
public interface CommentRepository extends CrudRepository<UserComment, Long> {

    @Query("SELECT comment FROM UserComment comment WHERE comment.thread = :thread AND comment.parent IS NULL")
    Iterable<UserComment> findAllByThread(UserThread thread);
}
