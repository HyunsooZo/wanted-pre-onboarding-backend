package com.wanted.dto.jobposting;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Api("채용공고 상세정보 Response")
public class JobPostingDetailResponse {
    private Long id;
    private String companyEmail;
    private String position;
    private String imageUrl;
    private Long reward;
    private String content;
    private List<String> techStacks;
    private List<JobPostingRelationsDto> relations;

    public static JobPostingDetailResponse from(JobPostingDetailDto jobPostingDetailDto) {
        return JobPostingDetailResponse.builder()
                .id(jobPostingDetailDto.getJobPosting().getId())
                .companyEmail(jobPostingDetailDto.getJobPosting().getCompany().getEmail())
                .position(jobPostingDetailDto.getJobPosting().getPosition())
                .imageUrl(jobPostingDetailDto.getJobPosting().getImageUrl())
                .reward(jobPostingDetailDto.getJobPosting().getReward())
                .content(jobPostingDetailDto.getJobPosting().getContent())
                .techStacks(jobPostingDetailDto.getJobPosting().getTechStacks())
                .relations(jobPostingDetailDto.getRelations())
                .build();
    }
}
