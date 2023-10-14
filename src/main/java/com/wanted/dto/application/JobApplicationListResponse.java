package com.wanted.dto.application;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "채용지원 목록 Response DTO")
public class JobApplicationListResponse {
    List<JobApplicationDto> applications;

    public static JobApplicationListResponse from(List<JobApplicationDto> applications) {
        return JobApplicationListResponse.builder()
                .applications(applications)
                .build();
    }
}
