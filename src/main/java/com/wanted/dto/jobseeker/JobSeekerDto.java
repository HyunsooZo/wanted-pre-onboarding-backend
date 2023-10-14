package com.wanted.dto.jobseeker;

import com.wanted.model.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ApiModel(value = "구직자 DTO")
public class JobSeekerDto {
    private String email;
    private String name;
    private String phone;
    private String resumeImageUrl;

    public static JobSeekerDto from(Member member) {
        return JobSeekerDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhone())
                .resumeImageUrl(member.getResumeImageUrl())
                .build();
    }
}
