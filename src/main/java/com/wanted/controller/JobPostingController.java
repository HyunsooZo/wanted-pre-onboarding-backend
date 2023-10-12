package com.wanted.controller;

import com.wanted.dto.JobPostingModificationRequest;
import com.wanted.dto.jobposting.*;
import com.wanted.service.JobPostingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RequestMapping("/api/job-postings")
@Api(tags = "채용공고 API", description = "채용공고와 관련된 API")
@RestController
public class JobPostingController {

    private final JobPostingService jobPostingService;

    @PostMapping
    @ApiOperation(value = "채용공고 등록", notes = "채용공고를 등록합니다.")
    public ResponseEntity<Void> addJobPosting(
            @Valid @RequestBody JobPostingRegistrationRequest jobPostingRequestDto) {

        jobPostingService.addJobPosting(jobPostingRequestDto);

        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping
    @ApiOperation(value = "채용공고 조회", notes = "채용공고를 조회합니다.")
    public ResponseEntity<JobPostingListResponse> getJobPostings() {

        List<JobPostingDto> jobPostingResponseDto =
                jobPostingService.getJobPostings();

        return ResponseEntity.status(OK)
                .body(JobPostingListResponse.from(jobPostingResponseDto));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "채용공고 상세 조회", notes = "채용공고 상세정보를 조회합니다.")
    public ResponseEntity<JobPostingDetailResponse> getJobPostings(
            @PathVariable Long id) {

        JobPostingDetailDto jobPostingDetailDto =
                jobPostingService.getJobPostingDetails(id);

        return ResponseEntity.status(OK)
                .body(JobPostingDetailResponse.from(jobPostingDetailDto));
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "채용공고 수정", notes = "채용공고 내용을 수정합니다. (수정안하는 필드는 null로 보내주세요.)")
    public ResponseEntity<Void> modifyJobPosting(
            @PathVariable Long id,
            @Valid @RequestBody JobPostingModificationRequest jobPostingModificationRequestDto) {

        jobPostingService.modifyJobPosting(id,jobPostingModificationRequestDto);

        return ResponseEntity.status(NO_CONTENT).build();
    }

}
