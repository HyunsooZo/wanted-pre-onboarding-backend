package com.wanted.repository;

import com.wanted.model.JobPosting;
import com.wanted.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    List<JobPosting> findByMember(Member member);

    @Query("SELECT jp FROM JobPosting jp WHERE " +
            "(:titleKeyword IS NULL OR jp.title IS NULL OR jp.title LIKE %:titleKeyword%) " +
            "AND (:techStackKeyword IS NULL OR jp.techStacks IS EMPTY OR :techStackKeyword MEMBER OF jp.techStacks) " +
            "AND (:regionKeyword IS NULL OR jp.region IS NULL OR jp.region LIKE %:regionKeyword%) " +
            "AND (:positionKeyword IS NULL OR jp.position IS NULL OR jp.position LIKE %:positionKeyword%)")
    List<JobPosting> customSearch(
            @Param("titleKeyword") String titleKeyword,
            @Param("techStackKeyword") String techStackKeyword,
            @Param("regionKeyword") String regionKeyword,
            @Param("positionKeyword") String positionKeyword
    );

}
