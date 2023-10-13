package com.wanted.dto.jobposting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
