package com.ignit.internship.repository.community;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.community.Community;
import com.ignit.internship.model.community.UserThread;

@Repository
public interface ThreadRepository extends CrudRepository<UserThread, Long> {
    Iterable<UserThread> findAllByCommunity(Community community);
}
