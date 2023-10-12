package com.wanted.dto.company;

import com.wanted.model.Company;
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
    private String address;
    private String phone;

    public static CompanyDto from(Company company) {
        return CompanyDto.builder()
                .id(company.getId())
                .email(company.getEmail())
                .name(company.getName())
                .address(company.getAddress())
                .phone(company.getPhone())
                .build();
    }
}
