package com.wanted.controller;

import com.wanted.component.EmailSender;
import com.wanted.dto.application.JobApplicationDto;
import com.wanted.service.JobApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RequiredArgsConstructor
@RequestMapping("/api/applications")
@Api(tags = "(채용공고에)지원 API", description = "채용공고에 지원합니다.")
@RestController
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;
    private final EmailSender emailSender;
    final String SUBJECT = "%s 님으로 부터 채용공고 지원서가 도착했습니다.";
    final String TEXT = "[채용공고] : %s\n" +
            "[지원자 정보] \n" +
            "이메일: %s\n" +
            "이름: %s\n" +
            "전화번호: %s\n" +
            "이력서링크: %s";

    @PostMapping("/job-posting/{jobPostingId}/member/{memberId}")
    @ApiOperation(value = "채용공고에 지원", notes = "채용공고에 지원합니다.")
    public ResponseEntity<Void> addApplication(
            @PathVariable Long memberId,
            @PathVariable Long jobPostingId) {

        JobApplicationDto jobApplicationDto =
                jobApplicationService.addApplication(memberId, jobPostingId);

        emailSender.sendEmail(
                jobApplicationDto.getJobPostingDto().getEmail(),
                String.format(SUBJECT, jobApplicationDto.getJobSeekerDto().getName()),
                String.format(TEXT,
                        jobApplicationDto.getJobPostingDto().getTitle(),
                        jobApplicationDto.getJobSeekerDto().getEmail(),
                        jobApplicationDto.getJobSeekerDto().getName(),
                        jobApplicationDto.getJobSeekerDto().getPhone(),
                        jobApplicationDto.getJobSeekerDto().getImageUrl()
                ));

        return ResponseEntity.status(CREATED).build();
    }

    @DeleteMapping("/{applicationId}/member/{memberId}")
    @ApiOperation(value = "지원서 삭제", notes = "지원서를 삭제합니다.")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long applicationId,
            @PathVariable Long memberId) {

        jobApplicationService.deleteApplication(memberId, applicationId);

        return ResponseEntity.status(NO_CONTENT).build();
    }

}
