package com.ignit.internship.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.UserComment;

@Repository
public interface CommentRepository extends CrudRepository<UserComment, Long> {}
