package com.wanted.dto.company;

import com.wanted.model.Member;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Api("기업 Dto")
public class CompanyDto {
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String imageUrl;

    public static CompanyDto from(Member member) {
        return CompanyDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhone())
                .imageUrl(member.getImageUrl())
                .build();
    }
}
