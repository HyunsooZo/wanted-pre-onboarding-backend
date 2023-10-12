package com.wanted.dto.jobposting;

import com.wanted.dto.company.CompanyDto;
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
    private CompanyDto company;
    private String position;
    private String imageUrl;
    private Long reward;
    private String content;
    private List<String> techStacks;
    private List<JobPostingRelationsDto> relations;

    public static JobPostingDetailResponse from(JobPostingDetailDto jobPostingDetailDto) {
        return JobPostingDetailResponse.builder()
                .id(jobPostingDetailDto.getJobPostingDto().getId())
                .company(jobPostingDetailDto.getCompany())
                .position(jobPostingDetailDto.getJobPostingDto().getPosition())
                .imageUrl(jobPostingDetailDto.getJobPostingDto().getImageUrl())
                .reward(jobPostingDetailDto.getJobPostingDto().getReward())
                .content(jobPostingDetailDto.getContent())
                .techStacks(jobPostingDetailDto.getJobPostingDto().getTechStacks())
                .relations(jobPostingDetailDto.getRelations())
                .build();
    }
}
