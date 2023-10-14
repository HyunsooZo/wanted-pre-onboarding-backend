package com.wanted.controller;

import com.wanted.dto.application.JobApplicationDto;
import com.wanted.dto.application.JobApplicationListResponse;
import com.wanted.service.JobApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping("/api/job-seekers")
@Api(tags = "구직자 API", description = "구직자 관련 API")
@RestController
public class JobSeekerController {
    private final JobApplicationService jobApplicationService;

    @GetMapping("{memberId}")
    @ApiOperation(value = "내가 지원한 지원내역 목록 조회", notes = "내가 지원한 모든 지원내역의 목록을 조회합니다.")
    public ResponseEntity<JobApplicationListResponse> getJobPostingsIApplied(
            @PathVariable Long memberId) {

        List<JobApplicationDto> applications =
                jobApplicationService.getApplications(memberId);

        return ResponseEntity.status(OK)
                .body(JobApplicationListResponse.from(applications));
    }

}
