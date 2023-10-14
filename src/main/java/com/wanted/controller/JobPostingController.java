package com.wanted.controller;

import com.wanted.dto.jobposting.*;
import com.wanted.service.ImageService;
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
    private final ImageService imageService;

    @PostMapping("/member/{memberId}")
    @ApiOperation(value = "채용공고 등록", notes = "채용공고를 등록합니다.")
    public ResponseEntity<Void> addJobPosting(
            @PathVariable Long memberId,
            @Valid @RequestBody JobPostingRegistrationRequest jobPostingRequestDto) {

        jobPostingService.addJobPosting(memberId, jobPostingRequestDto);

        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping
    @ApiOperation(
            value = "채용공고 검색/조회",
            notes = "쿼리스트링을 사용하지 않을 경우 전체 채용공고를 조회합니다." +
                    "쿼리스트링을 사용한 값에 대해서는 해당 값으로 필터링된 채용공고를 조회합니다."
    )
    public ResponseEntity<JobPostingListResponse> getJobPostings(
            @RequestParam(required = false) String titleKeyword,
            @RequestParam(required = false) String techStackKeyword,
            @RequestParam(required = false) String regionKeyword,
            @RequestParam(required = false) String positionKeyword
    ) {

        List<JobPostingDto> jobPostings = jobPostingService.getJobPostings(
                titleKeyword, techStackKeyword, regionKeyword, positionKeyword
        );

        return ResponseEntity.status(OK)
                .body(JobPostingListResponse.from(jobPostings));
    }

    @GetMapping("/{jobPostingId}")
    @ApiOperation(value = "채용공고 상세 조회", notes = "채용공고 상세정보를 조회합니다.")
    public ResponseEntity<JobPostingDetailResponse> getJobPostings(
            @PathVariable Long jobPostingId) {

        JobPostingDetailDto jobPostingDetailDto =
                jobPostingService.getJobPostingDetails(jobPostingId);

        return ResponseEntity.status(OK)
                .body(JobPostingDetailResponse.from(jobPostingDetailDto));
    }

    // 사전 과제에서는 토큰인증 과정이 생략되므로 직접 MemberId를 받아 검증하도록 구현
    @PatchMapping("/{jobPostingId}/member/{memberId}")
    @ApiOperation(value = "채용공고 수정", notes = "채용공고 내용을 수정합니다. (수정안하는 필드는 null로 보내주세요.)")
    public ResponseEntity<Void> modifyJobPosting(
            @PathVariable Long jobPostingId,
            @PathVariable Long memberId,
            @Valid @RequestBody JobPostingModificationRequest jobPostingModificationRequestDto) {

        jobPostingService.modifyJobPosting(
                jobPostingId, jobPostingModificationRequestDto, memberId
        );

        return ResponseEntity.status(NO_CONTENT).build();
    }

    // 사전 과제에서는 토큰인증 과정이 생략되므로 직접 MemberId를 받아 검증하도록 구현
    @DeleteMapping("/{jobPostingId}/member/{memberId}")
    @ApiOperation(value = "채용공고 삭제", notes = "채용공고를 삭제합니다.")
    public ResponseEntity<Void> deleteJobPosting(
            @PathVariable Long jobPostingId,
            @PathVariable Long memberId) {

        String targetImageUrl =
                jobPostingService.deleteJobPosting(jobPostingId, memberId);

        //채용공고 삭제 시 공고이미지도 삭제
        imageService.deleteFile(targetImageUrl);

        return ResponseEntity.status(NO_CONTENT).build();
    }

}
