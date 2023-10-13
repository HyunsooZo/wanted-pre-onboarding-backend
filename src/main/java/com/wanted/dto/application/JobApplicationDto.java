package com.wanted.dto.application;

import com.wanted.dto.jobposting.JobPostingDto;
import com.wanted.dto.jobseeker.JobSeekerDto;
import com.wanted.model.JobApplication;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JobApplicationDto {
    private JobPostingDto jobPostingDto;
    private JobSeekerDto jobSeekerDto;

    public static JobApplicationDto from(JobSeekerDto jobSeekerDto,
                                         JobPostingDto jobPostingDto) {

        return JobApplicationDto.builder()
                .jobSeekerDto(jobSeekerDto)
                .jobPostingDto(jobPostingDto)
                .build();
    }
    public static JobApplicationDto from(JobApplication jobApplication) {

        return JobApplicationDto.builder()
                .jobSeekerDto(JobSeekerDto.from(jobApplication.getApplicant()))
                .jobPostingDto(JobPostingDto.from(jobApplication.getJobPosting()))
                .build();
    }
}
