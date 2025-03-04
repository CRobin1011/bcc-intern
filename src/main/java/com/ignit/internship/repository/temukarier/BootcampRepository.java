package com.ignit.internship.repository.temukarier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.temukarier.Bootcamp;

@Repository
public interface BootcampRepository extends JpaRepository<Bootcamp, Long> {
    
    @Query("SELECT p FROM Project p JOIN p.tags t WHERE t.name = :tag")
    Page<Bootcamp> findByTagName(@Param("tag") String tag, Pageable pageable);
}
