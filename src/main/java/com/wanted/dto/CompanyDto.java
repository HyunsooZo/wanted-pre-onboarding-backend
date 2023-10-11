package com.wanted.dto;

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
    private String Name;
    private String Address;
    private String Phone;

    public static CompanyDto from(Company company) {
        return CompanyDto.builder()
                .id(company.getId())
                .email(company.getEmail())
                .Name(company.getName())
                .Address(company.getAddress())
                .Phone(company.getPhone())
                .build();
    }
}
