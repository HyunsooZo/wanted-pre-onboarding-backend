package com.wanted.service;

import com.wanted.dto.jobposting.*;
import com.wanted.exception.CustomException;
import com.wanted.exception.ErrorCode;
import com.wanted.model.Company;
import com.wanted.model.JobPosting;
import com.wanted.repository.CompanyRepository;
import com.wanted.repository.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public void addJobPosting(JobPostingRegistrationRequest jobPostingRequestDto) {

        Company company = companyRepository
                .findByEmail(jobPostingRequestDto.getCompanyEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.COMPANY_NOT_FOUND));

        jobPostingRepository.save(JobPosting.from(jobPostingRequestDto, company));
    }

    public List<JobPostingDto> getJobPostings() {

        return jobPostingRepository.findAll().stream()
                .map(JobPostingDto::from)
                .collect(Collectors.toList());
    }

    public JobPostingDetailDto getJobPostingDetails(Long companyId) {

        JobPosting targetJobPosting = jobPostingRepository.findById(companyId)
                .orElseThrow(() -> new CustomException(ErrorCode.JOB_POSTING_NOT_FOUND));

        List<JobPostingRelationsDto> relations =
                jobPostingRepository.findByCompany(targetJobPosting.getCompany())
                        .stream()
                        .filter(jobPosting -> !Objects.equals(jobPosting.getId(), companyId))
                        .map(JobPostingRelationsDto::from)
                        .collect(Collectors.toList());

        return JobPostingDetailDto.from(targetJobPosting, relations);
    }

}
