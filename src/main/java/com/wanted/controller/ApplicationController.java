package com.wanted.controller;

import com.wanted.component.EmailSender;
import com.wanted.dto.application.ApplicationDto;
import com.wanted.service.JobApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/api/applications")
@Api(tags = "(채용공고에)지원 API", description = "채용공고에 지원합니다.")
@RestController
public class ApplicationController {
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

        ApplicationDto applicationDto =
                jobApplicationService.addApplication(memberId, jobPostingId);

        emailSender.sendEmail(
                applicationDto.getJobPostingDto().getEmail(),
                String.format(SUBJECT, applicationDto.getJobSeekerDto().getName()),
                String.format(TEXT,
                        applicationDto.getJobPostingDto().getTitle(),
                        applicationDto.getJobSeekerDto().getEmail(),
                        applicationDto.getJobSeekerDto().getName(),
                        applicationDto.getJobSeekerDto().getPhone(),
                        applicationDto.getJobSeekerDto().getImageUrl()
                ));

        return ResponseEntity.status(CREATED).build();
    }

}
