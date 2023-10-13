package com.wanted.dto.jobseeker;

import com.wanted.model.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JobSeekerDto {
    private String email;
    private String name;
    private String phone;
    private String imageUrl;

    public static JobSeekerDto from(Member member) {
        return JobSeekerDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhone())
                .imageUrl(member.getImageUrl())
                .build();
    }
}
