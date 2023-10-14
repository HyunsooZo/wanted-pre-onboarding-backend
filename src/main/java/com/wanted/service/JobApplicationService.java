package com.wanted.service;

import com.wanted.dto.application.JobApplicationDto;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.wanted.enums.MemberRole.JOB_SEEKER;
import static com.wanted.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class JobApplicationService {
    private final MemberRepository memberRepository;
    private final JobPostingRepository jobPostingRepository;
    private final JobApplicationRepository jobApplicationRepository;

    @Transactional
    public JobApplicationDto addApplication(Long memberId, Long jobPostingId) {
        Member member = getJobSeekerById(memberId);
        JobPosting jobPosting = getJobPostingById(jobPostingId);

        checkIfAlreadyApplied(member, jobPosting);

        jobApplicationRepository.save(JobApplication.from(member, jobPosting));

        JobSeekerDto jobSeekerDto = JobSeekerDto.from(member);
        JobPostingDto jobPostingDto = JobPostingDto.from(jobPosting);

        return JobApplicationDto.from(jobSeekerDto, jobPostingDto);
    }

    @Transactional
    public void deleteApplication(Long memberId, Long applicationId) {
        Member member = getJobSeekerById(memberId);

        JobApplication jobApplication =
                findJobApplicationByIdAndCheckOwnership(member, applicationId);

        jobApplicationRepository.delete(jobApplication);

    }

    @Transactional(readOnly = true)
    public List<JobApplicationDto> getApplications(Long memberId) {
        Member member = getJobSeekerById(memberId);

        List<JobApplication> jobApplications =
                jobApplicationRepository.findAllByApplicant(member);

        return jobApplications.stream()
                .map(JobApplicationDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<JobApplicationDto> getApplicationsIGot(Long companyId) {
        Member company = memberRepository.findById(companyId)
                .orElseThrow(() -> new CustomException(COMPANY_NOT_FOUND));

        List<JobPosting> jobPostings = jobPostingRepository.findByMember(company);

        return jobPostings.stream()
                .map(jobApplicationRepository::findAllByJobPosting)
                .flatMap(List::stream)
                .map(JobApplicationDto::from)
                .collect(Collectors.toList());
    }

    private JobApplication findJobApplicationByIdAndCheckOwnership(Member member,
                                                                   Long applicationId) {
        JobApplication jobApplication =
                jobApplicationRepository.findById(applicationId)
                        .orElseThrow(() -> new CustomException(APPLICATION_NOT_FOUND));

        if (!jobApplication.getApplicant().equals(member)) {
            throw new CustomException(NOT_MY_APPLICATION);
        }

        return jobApplication;
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
