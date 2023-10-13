package com.wanted.service;

import com.wanted.dto.application.ApplicationDto;
import com.wanted.dto.jobseeker.JobSeekerDto;
import com.wanted.dto.jobposting.JobPostingDto;
import com.wanted.exception.CustomException;
import com.wanted.model.JobApplication;
import com.wanted.model.JobPosting;
import com.wanted.model.Member;
import com.wanted.repository.JobApplicationRepository;
import com.wanted.repository.JobPostingRepository;
import com.wanted.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.wanted.enums.MemberRole.JOB_SEEKER;
import static com.wanted.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class JobApplicationService {
    private final MemberRepository memberRepository;
    private final JobPostingRepository jobPostingRepository;
    private final JobApplicationRepository jobApplicationRepository;

    @Transactional
    public ApplicationDto addApplication(Long memberId, Long jobPostingId) {
        Member member = getJobSeekerById(memberId);
        JobPosting jobPosting = getJobPostingById(jobPostingId);

        checkIfAlreadyApplied(member, jobPosting);

        jobApplicationRepository.save(JobApplication.from(member, jobPosting));

        JobSeekerDto jobSeekerDto = JobSeekerDto.from(member);
        JobPostingDto jobPostingDto = JobPostingDto.from(jobPosting);

        return ApplicationDto.from(jobSeekerDto, jobPostingDto);
    }

    private Member getJobSeekerById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(JOB_SEEKER_NOT_FOUND));
        if (!member.getRole().equals(JOB_SEEKER)) {
            throw new CustomException(NOT_JOB_SEEKER_MEMBER);
        }
        return member;
    }

    private JobPosting getJobPostingById(Long jobPostingId) {
        return jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new CustomException(JOB_POSTING_NOT_FOUND));
    }

    private void checkIfAlreadyApplied(Member member, JobPosting jobPosting) {
        if (jobApplicationRepository.findByApplicantAndJobPosting(member, jobPosting).isPresent()) {
            throw new CustomException(ALREADY_APPLIED);
        }
    }

}
