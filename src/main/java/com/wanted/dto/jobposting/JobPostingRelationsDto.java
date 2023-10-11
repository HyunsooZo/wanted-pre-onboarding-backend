package com.wanted.dto.jobposting;

import com.wanted.model.JobPosting;
import io.swagger.annotations.Api;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Api("연관 채용공고 Dto")
public class JobPostingRelationsDto {
    private Long id;
    private String title;

    public static JobPostingRelationsDto from(JobPosting jobPosting) {
        return JobPostingRelationsDto.builder()
                .id(jobPosting.getId())
                .title(jobPosting.getTitle())
                .build();
    }
}
