package com.wanted.dto;

import com.wanted.model.JobPosting;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Api("채용공고 Dto")
public class JobPostingDto {
    private Long id;
    private String companyEmail;
    private String position;
    private Long reward;
    private String content;
    private List<String> techStacks;

    public static JobPostingDto from(JobPosting jobPosting) {
        return JobPostingDto.builder()
                .id(jobPosting.getId())
                .companyEmail(jobPosting.getCompany().getEmail())
                .position(jobPosting.getPosition())
                .reward(jobPosting.getReward())
                .content(jobPosting.getContent())
                .techStacks(jobPosting.getTechStacks())
                .build();
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Api("채용공고 등록 Response")
    public static class PostingRequest {

        private String imageUrl;
        private String position;

        @Min(value = 0, message = "보상은 0원 이상이어야 합니다.")
        private Long reward;

        //해당 프로젝트에선 인증과정이 생략되므로 id(실제 사용자가 로그인시 사용하는 ID)를 입력받는다.
        @NotBlank(message = "Id는 필수입력 항목입니다.")
        private String companyEmail;

        @NotBlank(message = "채용공고 본문은 필수입력 항목입니다.")
        @Size(min = 10, message = "본문은 최소 10자 이상 입력해주세요.")
        private String content;

        @NotEmpty(message = "최소 한 개 이상의 기술스택을 입력하세요")
        private List<String> techStacks;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PostingResponse {
        private List<JobPostingDto> techStacks;

        public static PostingResponse from(List<JobPostingDto> techStacks) {
            return PostingResponse.builder()
                    .techStacks(techStacks)
                    .build();
        }
    }
}
