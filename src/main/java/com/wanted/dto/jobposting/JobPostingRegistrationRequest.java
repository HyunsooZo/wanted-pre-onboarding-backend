package com.wanted.dto.jobposting;

import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "채용공고 등록 Request DTO")
public class JobPostingRegistrationRequest {
    private String imageUrl;
    private String position;

    @Min(value = 0, message = "보상은 0원 이상이어야 합니다.")
    private Long reward;

    @NotBlank(message = "채용공고 제목는 필수입력 항목입니다.")
    private String title;

    @NotBlank(message = "Id는 필수입력 항목입니다.")
    private String companyEmail;

    @NotBlank(message = "채용공고 본문은 필수입력 항목입니다.")
    @Size(min = 10, message = "본문은 최소 10자 이상 입력해주세요.")
    private String content;

    @NotEmpty(message = "최소 한 개 이상의 기술스택을 입력하세요")
    private List<String> techStacks;
}
