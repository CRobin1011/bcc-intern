package com.ignit.internship.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.UserThread;

@Repository
public interface ThreadRepository extends CrudRepository<UserThread, Long> {}
