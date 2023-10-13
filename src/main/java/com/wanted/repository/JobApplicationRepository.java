package com.wanted.repository;

import com.wanted.model.JobApplication;
import com.wanted.model.JobPosting;
import com.wanted.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    Optional<JobApplication> findByApplicantAndJobPosting(Member member,
                                                          JobPosting jobPosting);
}
