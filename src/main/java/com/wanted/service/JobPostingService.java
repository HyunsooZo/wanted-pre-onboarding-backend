package com.wanted.service;

import com.wanted.dto.jobposting.JobPostingModificationRequest;
import com.wanted.dto.company.CompanyDto;
import com.wanted.dto.jobposting.JobPostingDetailDto;
import com.wanted.dto.jobposting.JobPostingDto;
import com.wanted.dto.jobposting.JobPostingRegistrationRequest;
import com.wanted.dto.jobposting.JobPostingRelationsDto;
import com.wanted.enums.MemberRole;
import com.wanted.exception.CustomException;
import com.wanted.exception.ErrorCode;
import com.wanted.model.Member;
import com.wanted.model.JobPosting;
import com.wanted.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    @Transactional
    public void addJobPosting(JobPostingRegistrationRequest jobPostingRequestDto) {

        Member member = memberRepository
                .findByEmail(jobPostingRequestDto.getCompanyEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.COMPANY_NOT_FOUND));

        // 사전과제 요구사항에서 인증이 생략되었기 때문에 서비스레이어에서 직접 검증
        validateMemberIsCompany(member);

        JobPosting jobPosting = JobPosting.from(jobPostingRequestDto, member);
        jobPostingRepository.save(jobPosting);
    }

    public List<JobPostingDto> getJobPostings(String titleKeyword,
                                              String techStackKeyword,
                                              String regionKeyword,
                                              String positionKeyword) {

        List<JobPosting> jobPostingsSearched = jobPostingRepository.customSearch(
                titleKeyword, techStackKeyword, regionKeyword, positionKeyword
        );

        return jobPostingsSearched.stream()
                .map(JobPostingDto::from)
                .collect(Collectors.toList());
    }

    public JobPostingDetailDto getJobPostingDetails(Long companyId) {

        JobPosting targetJobPosting = jobPostingRepository.findById(companyId)
                .orElseThrow(() -> new CustomException(ErrorCode.JOB_POSTING_NOT_FOUND));

        List<JobPostingRelationsDto> relations =
                jobPostingRepository.findByMember(targetJobPosting.getMember())
                        .stream()
                        .filter(jobPosting -> !Objects.equals(jobPosting.getId(), companyId))
                        .map(JobPostingRelationsDto::from)
                        .collect(Collectors.toList());

        Member member = memberRepository.findById(targetJobPosting.getMember().getId())
                .orElseThrow(() -> new CustomException(ErrorCode.COMPANY_NOT_FOUND));

        return JobPostingDetailDto.from(
                JobPostingDto.from(targetJobPosting),
                CompanyDto.from(member),
                targetJobPosting.getContent(),
                relations
        );
    }

    public void modifyJobPosting(Long jobPostingId,
                                 JobPostingModificationRequest ModificationRequestDto,
                                 Long memberId) {

        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new CustomException(ErrorCode.JOB_POSTING_NOT_FOUND));

        // 권한 검증(본인의 게시물 인지)
        checkPermission(jobPosting, memberId);

        // 빈값이거나 null 이 아닌 경우에만 수정
        updateIfNotNull(jobPosting::setContent, ModificationRequestDto.getContent());
        updateIfNotNull(jobPosting::setImageUrl, ModificationRequestDto.getImageUrl());
        updateIfNotNull(jobPosting::setReward, ModificationRequestDto.getReward());
        updateIfNotNull(jobPosting::setPosition, ModificationRequestDto.getPosition());
        updateIfNotNull(jobPosting::setTechStacks, ModificationRequestDto.getTechStacks());
        updateIfNotNull(jobPosting::setTitle, ModificationRequestDto.getTitle());
        updateIfNotNull(jobPosting::setCountry, ModificationRequestDto.getCountry());
        updateIfNotNull(jobPosting::setRegion, ModificationRequestDto.getRegion());

        jobPostingRepository.save(jobPosting);
    }

    private <T> void updateIfNotNull(Consumer<T> setter, T value) {
        if (value != null && (!(value instanceof String) || !((String) value).isEmpty())) {
            setter.accept(value);
        }
    }

    @Transactional
    public String deleteJobPosting(Long jobPostingId, Long memberId) {

        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new CustomException(ErrorCode.JOB_POSTING_NOT_FOUND));

        // 권한 검증(본인의 게시물 인지)
        checkPermission(jobPosting, memberId);

        // 사전과제 요구사항에서 인증이 생략되었기 때문에 서비스레이어에서 직접 검증
        validateMemberIsCompany(jobPosting.getMember());

        String imageUrl = jobPosting.getImageUrl();

        jobPostingRepository.delete(jobPosting);

        return imageUrl;
    }

    private void validateMemberIsCompany(Member member) {
        if (member.getRole() != MemberRole.COMPANY) {
            throw new CustomException(ErrorCode.NOT_COMPANY_MEMBER);
        }
    }

    private void checkPermission(JobPosting jobPosting, Long memberId) {
        if (!jobPosting.getMember().getId().equals(memberId)) {
            throw new CustomException(ErrorCode.PERMISSION_DENIED);
        }
    }
}
