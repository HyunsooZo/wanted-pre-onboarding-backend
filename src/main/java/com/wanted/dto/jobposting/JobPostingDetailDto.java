package com.wanted.dto.jobposting;

import com.wanted.dto.company.CompanyDto;
import io.swagger.annotations.Api;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Api("채용공고 상세정보 Dto")
public class JobPostingDetailDto {
    private JobPostingDto jobPostingDto;
    private String content;
    private CompanyDto company;
    private List<JobPostingRelationsDto> relations;

    public static JobPostingDetailDto from(JobPostingDto targetJobPosting,
                                           CompanyDto companyDto,
                                           String content,
                                           List<JobPostingRelationsDto> relations) {
        return JobPostingDetailDto.builder()
                .jobPostingDto(targetJobPosting)
                .content(content)
                .company(companyDto)
                .relations(relations)
                .build();
    }
}
