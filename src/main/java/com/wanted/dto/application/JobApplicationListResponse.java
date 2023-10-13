package com.wanted.dto.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobApplicationListResponse {
    List<JobApplicationDto> applications;
    public static JobApplicationListResponse from(List<JobApplicationDto> applications) {
        return JobApplicationListResponse.builder()
                .applications(applications)
                .build();
    }
}
