package com.wanted.dto.jobposting;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "채용공고 수정 Request DTO")
public class JobPostingModificationRequest {
    private String title;
    private String imageUrl;
    private String position;
    private String country;
    private String region;
    private Long reward;
    private String content;
    private List<String> techStacks;
}
