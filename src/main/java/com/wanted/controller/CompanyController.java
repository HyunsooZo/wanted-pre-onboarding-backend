package com.wanted.controller;

import com.wanted.dto.application.JobApplicationDto;
import com.wanted.dto.application.JobApplicationListResponse;
import com.wanted.service.JobApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RequestMapping("/api/companies")
@Api(tags = "회사 API", description = "회사 관련 API")
@RestController
public class CompanyController {
    private final JobApplicationService jobApplicationService;

    @GetMapping("/{companyId}/applications")
    @ApiOperation(
            value = "내게 지원된 모든 채용지원 조회",
            notes = "해당기업에 신청된 모든 지원내용을 조회합니다."
    )
    public ResponseEntity<JobApplicationListResponse> getJobPostingsIPosted(
            @PathVariable Long companyId) {

        List<JobApplicationDto> applicationsIPosted =
                jobApplicationService.getApplicationsIGot(companyId);

        return ResponseEntity.status(OK)
                .body(JobApplicationListResponse.from(applicationsIPosted));
    }
}
