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
@Api("채용공고 전체목록 Response")
public class JobPostingListResponse {
    private List<JobPostingDto> jobPostings;

    public static JobPostingListResponse from(List<JobPostingDto> techStacks) {
        return JobPostingListResponse.builder()
                .jobPostings(techStacks)
                .build();
    }
}
