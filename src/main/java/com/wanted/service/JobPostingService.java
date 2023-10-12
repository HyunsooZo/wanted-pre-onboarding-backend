package com.wanted.service;

import com.wanted.dto.JobPostingModificationRequest;
import com.wanted.dto.company.CompanyDto;
import com.wanted.dto.jobposting.JobPostingDetailDto;
import com.wanted.dto.jobposting.JobPostingDto;
import com.wanted.dto.jobposting.JobPostingRegistrationRequest;
import com.wanted.dto.jobposting.JobPostingRelationsDto;
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
import java.util.function.Consumer;
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

        Company company = companyRepository.findById(targetJobPosting.getCompany().getId())
                .orElseThrow(() -> new CustomException(ErrorCode.COMPANY_NOT_FOUND));

        return JobPostingDetailDto.from(
                JobPostingDto.from(targetJobPosting),
                CompanyDto.from(company),
                targetJobPosting.getContent(),
                relations
        );
    }

    public void modifyJobPosting(Long id,
                                 JobPostingModificationRequest ModificationRequestDto) {

        JobPosting jobPosting = jobPostingRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.JOB_POSTING_NOT_FOUND));

        // 빈값이거나 null 이 아닌 경우에만 수정
        updateIfNotNull(jobPosting::setContent, ModificationRequestDto.getContent());
        updateIfNotNull(jobPosting::setImageUrl, ModificationRequestDto.getImageUrl());
        updateIfNotNull(jobPosting::setReward, ModificationRequestDto.getReward());
        updateIfNotNull(jobPosting::setPosition, ModificationRequestDto.getPosition());
        updateIfNotNull(jobPosting::setTechStacks, ModificationRequestDto.getTechStacks());

        jobPostingRepository.save(jobPosting);
    }

    private <T> void updateIfNotNull(Consumer<T> setter, T value) {
        if (value != null && (!(value instanceof String) || !((String) value).isEmpty())) {
            setter.accept(value);
        }
    }

    public String deleteJobPosting(Long id) {

        JobPosting jobPosting = jobPostingRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.JOB_POSTING_NOT_FOUND));

        String imageUrl = jobPosting.getImageUrl();

        jobPostingRepository.delete(jobPosting);

        return imageUrl;
    }

}
