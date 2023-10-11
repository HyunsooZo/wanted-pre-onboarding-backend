package com.wanted.controller;

import com.wanted.dto.JobPostingDto;
import com.wanted.service.JobPostingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

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

}
