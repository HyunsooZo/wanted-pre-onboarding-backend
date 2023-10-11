package com.wanted.service;

import com.wanted.dto.JobPostingDto;
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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public void addJobPosting(JobPostingDto.PostingRequest jobPostingRequestDto) {

        Company company = companyRepository
                .findByEmail(jobPostingRequestDto.getCompanyEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.COMPANY_NOT_FOUND));

        jobPostingRepository.save(JobPosting.from(jobPostingRequestDto, company));
    }

}
