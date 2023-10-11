package com.wanted.dto.jobposting;

import com.wanted.model.JobPosting;
import io.swagger.annotations.Api;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Api("채용공고 상세정보 Dto")
public class JobPostingDetailDto {
    private JobPosting jobPosting;
    private List<JobPostingRelationsDto> relations;

    public static JobPostingDetailDto from(JobPosting targetJobPosting, List<JobPostingRelationsDto> relations) {
        return JobPostingDetailDto.builder()
                .jobPosting(targetJobPosting)
                .relations(relations)
                .build();
    }
}
