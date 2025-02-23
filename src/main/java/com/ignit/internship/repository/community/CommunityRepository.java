package com.ignit.internship.repository.community;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.community.Community;

@Repository
public interface CommunityRepository extends CrudRepository<Community, Long> {}
