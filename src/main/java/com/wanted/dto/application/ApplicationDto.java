package com.wanted.dto.application;

import com.wanted.dto.jobposting.JobPostingDto;
import com.wanted.dto.jobseeker.JobSeekerDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApplicationDto {
    private JobPostingDto jobPostingDto;
    private JobSeekerDto jobSeekerDto;

    public static ApplicationDto from(JobSeekerDto jobSeekerDto,
                                      JobPostingDto jobPostingDto) {

        return ApplicationDto.builder()
                .jobSeekerDto(jobSeekerDto)
                .jobPostingDto(jobPostingDto)
                .build();
    }
}
