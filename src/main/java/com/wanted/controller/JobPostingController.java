package com.wanted.controller;

import com.wanted.dto.JobPostingDto;
import com.wanted.service.JobPostingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping("/api/job-postings")
@Api(tags = "채용공고 API", description = "채용공고와 관련된 API")
@RestController
public class JobPostingController {

    private final JobPostingService jobPostingService;

    @PostMapping
    @ApiOperation(value = "채용공고 등록", notes = "채용공고를 등록합니다.")
    public ResponseEntity<Void> addJobPosting(
            @Valid @RequestBody JobPostingDto.PostingRequest jobPostingRequestDto) {

        jobPostingService.addJobPosting(jobPostingRequestDto);

        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping
    @ApiOperation(value = "채용공고 조회", notes = "채용공고를 조회합니다.")
    public ResponseEntity<JobPostingDto.PostingResponse> getJobPostings() {

        List<JobPostingDto> jobPostingResponseDto =
                jobPostingService.getJobPostings();

        return ResponseEntity.status(OK)
                .body(JobPostingDto.PostingResponse.from(jobPostingResponseDto));
    }

}
