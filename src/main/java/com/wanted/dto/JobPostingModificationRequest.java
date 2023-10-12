package com.wanted.dto;

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
    private Long reward;
    private String content;
    private List<String> techStacks;
}
