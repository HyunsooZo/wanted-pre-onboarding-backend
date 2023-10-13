package com.wanted.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class JobApplication extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member", referencedColumnName = "id")
    private Member applicant;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "job_posting", referencedColumnName = "id")
    private JobPosting jobPosting;

    public static JobApplication from(Member member, JobPosting jobPosting) {
        return JobApplication.builder()
                .applicant(member)
                .jobPosting(jobPosting)
                .build();
    }
}
