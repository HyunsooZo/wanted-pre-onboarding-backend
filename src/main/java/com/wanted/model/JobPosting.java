package com.wanted.model;

import com.wanted.dto.JobPostingDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class JobPosting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "company", referencedColumnName = "id")
    private Company company;

    private String imageUrl;
    private String position;
    private int reward;
    private String content;

    @ElementCollection
    @CollectionTable(
            name = "job_posting_tech_stacks",
            joinColumns = @JoinColumn(name = "job_posting_id")
    )
    @Column(name = "tech_stack")
    private List<String> techStacks;

    public static JobPosting from(
            JobPostingDto.PostingRequest jobPostingRequestDto,
            Company company) {
        return JobPosting.builder()
                .imageUrl(jobPostingRequestDto.getImageUrl())
                .position(jobPostingRequestDto.getPosition())
                .reward(jobPostingRequestDto.getReward())
                .content(jobPostingRequestDto.getContent())
                .techStacks(jobPostingRequestDto.getTechStacks())
                .company(company)
                .build();
    }
}
