package com.wanted.service;

import com.wanted.dto.company.CompanyDto;
import com.wanted.dto.jobposting.*;
import com.wanted.exception.CustomException;
import com.wanted.model.JobPosting;
import com.wanted.model.Member;
import com.wanted.repository.JobPostingRepository;
import com.wanted.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.wanted.enums.MemberRole.COMPANY;
import static com.wanted.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void addJobPosting(Long memberId,
                              JobPostingRegistrationRequest jobPostingRequestDto) {

        Member member = memberRepository
                .findByIdAndRole(memberId, COMPANY)
                .orElseThrow(() -> new CustomException(COMPANY_NOT_FOUND));

        // 사전과제 요구사항에서 인증이 생략되었기 때문에 서비스레이어에서 직접 검증
        validateMemberIsCompany(member);

        JobPosting jobPosting = JobPosting.from(jobPostingRequestDto, member);
        jobPostingRepository.save(jobPosting);
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public JobPostingDetailDto getJobPostingDetails(Long companyId) {

        JobPosting targetJobPosting = getJobPostingById(companyId);

        List<JobPostingRelationsDto> relations =
                getJobPostingRelations(targetJobPosting);

        Member member = getMemberById(targetJobPosting.getMember().getId());

        return JobPostingDetailDto.from(
                JobPostingDto.from(targetJobPosting),
                CompanyDto.from(member),
                targetJobPosting.getContent(),
                relations
        );
    }

    @Transactional
    public void modifyJobPosting(Long jobPostingId,
                                 JobPostingModificationRequest modificationRequest,
                                 Long memberId) {

        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new CustomException(JOB_POSTING_NOT_FOUND));

        // 권한 검증(본인의 게시물 인지)
        checkPermission(jobPosting, memberId);

        // 빈값이거나 null 이 아닌 경우에만 수정
        updateJobPostingFields(jobPosting, modificationRequest);

        jobPostingRepository.save(jobPosting);
    }

    @Transactional
    public String deleteJobPosting(Long jobPostingId, Long memberId) {

        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new CustomException(JOB_POSTING_NOT_FOUND));

        // 권한 검증(본인의 게시물 인지)
        checkPermission(jobPosting, memberId);

        // 사전과제 요구사항에서 인증이 생략되었기 때문에 서비스레이어에서 직접 검증
        validateMemberIsCompany(jobPosting.getMember());

        String imageUrl = jobPosting.getImageUrl();

        jobPostingRepository.delete(jobPosting);

        return imageUrl;
    }

    private void validateMemberIsCompany(Member member) {
        if (member.getRole() != COMPANY) {
            throw new CustomException(NOT_COMPANY_MEMBER);
        }
    }

    private void checkPermission(JobPosting jobPosting, Long memberId) {
        if (!jobPosting.getMember().getId().equals(memberId)) {
            throw new CustomException(PERMISSION_DENIED);
        }
    }

    private <T> void updateIfNotNull(Consumer<T> setter, T value) {
        if (value != null && (!(value instanceof String) || !((String) value).isEmpty())) {
            setter.accept(value);
        }
    }

    private JobPosting getJobPostingById(Long companyId) {
        return jobPostingRepository.findById(companyId)
                .orElseThrow(() -> new CustomException(JOB_POSTING_NOT_FOUND));
    }

    private List<JobPostingRelationsDto> getJobPostingRelations(JobPosting targetJobPosting) {
        return jobPostingRepository
                .findByMember(targetJobPosting.getMember())
                .stream()
                .filter(jobPosting -> !Objects.equals(jobPosting.getId(), targetJobPosting.getId()))
                .map(JobPostingRelationsDto::from)
                .collect(Collectors.toList());
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(COMPANY_NOT_FOUND));
    }

    private void updateJobPostingFields(JobPosting jobPosting,
                                        JobPostingModificationRequest modificationRequestDto) {
        updateIfNotNull(jobPosting::setContent, modificationRequestDto.getContent());
        updateIfNotNull(jobPosting::setImageUrl, modificationRequestDto.getImageUrl());
        updateIfNotNull(jobPosting::setReward, modificationRequestDto.getReward());
        updateIfNotNull(jobPosting::setPosition, modificationRequestDto.getPosition());
        updateIfNotNull(jobPosting::setTechStacks, modificationRequestDto.getTechStacks());
        updateIfNotNull(jobPosting::setTitle, modificationRequestDto.getTitle());
        updateIfNotNull(jobPosting::setCountry, modificationRequestDto.getCountry());
        updateIfNotNull(jobPosting::setRegion, modificationRequestDto.getRegion());
    }
}
