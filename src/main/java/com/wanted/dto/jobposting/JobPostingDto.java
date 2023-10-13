package com.wanted.dto.jobposting;

import com.wanted.model.JobPosting;
import io.swagger.annotations.Api;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Api("채용공고 Dto")
public class JobPostingDto {
    private Long id;
    private String imageUrl;
    private String title;
    private String companyName;
    private String position;
    private String email;
    private String country;
    private String region;
    private Long reward;
    private List<String> techStacks;

    public static JobPostingDto from(JobPosting jobPosting) {
        return JobPostingDto.builder()
                .id(jobPosting.getId())
                .imageUrl(jobPosting.getImageUrl())
                .title(jobPosting.getTitle())
                .companyName(jobPosting.getMember().getName())
                .position(jobPosting.getPosition())
                .email(jobPosting.getMember().getEmail())
                .country(jobPosting.getCountry())
                .region(jobPosting.getRegion())
                .reward(jobPosting.getReward())
                .techStacks(jobPosting.getTechStacks())
                .build();
    }

}
