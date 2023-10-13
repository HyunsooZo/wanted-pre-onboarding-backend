package com.wanted.model;

import com.wanted.dto.jobposting.JobPostingRegistrationRequest;
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
    @JoinColumn(name = "member", referencedColumnName = "id")
    private Member member;

    private String title;
    private String imageUrl;
    private String country;
    private String region;
    private String position;
    private Long reward;
    private String content;

    @ElementCollection
    @CollectionTable(
            name = "job_posting_tech_stacks",
            joinColumns = @JoinColumn(name = "job_posting_id")
    )
    @Column(name = "tech_stack")
    private List<String> techStacks;

    public static JobPosting from(
            JobPostingRegistrationRequest jobPostingRequestDto,
            Member member) {
        return JobPosting.builder()
                .title(jobPostingRequestDto.getTitle())
                .imageUrl(jobPostingRequestDto.getImageUrl())
                .position(jobPostingRequestDto.getPosition())
                .reward(jobPostingRequestDto.getReward())
                .content(jobPostingRequestDto.getContent())
                .techStacks(jobPostingRequestDto.getTechStacks())
                .member(member)
                .build();
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setReward(Long reward) {
        this.reward = reward;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTechStacks(List<String> techStacks) {
        this.techStacks = techStacks;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setRegion(String region) {
        this.region = region;
    }
}
