package com.wanted.service;

import com.wanted.dto.application.JobApplicationDto;
import com.wanted.enums.MemberRole;
import com.wanted.exception.CustomException;
import com.wanted.model.JobApplication;
import com.wanted.model.JobPosting;
import com.wanted.model.Member;
import com.wanted.repository.JobApplicationRepository;
import com.wanted.repository.JobPostingRepository;
import com.wanted.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.wanted.enums.MemberRole.JOB_SEEKER;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("구직 관련 테스트코드")
class JobApplicationServiceTest {

    private JobApplicationService jobApplicationService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JobPostingRepository jobPostingRepository;

    @Mock
    private JobApplicationRepository applicationRepository;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        jobApplicationService =
                new JobApplicationService(
                        memberRepository, jobPostingRepository, applicationRepository
                );
    }

    static Member member = Member.builder()
            .id(1L)
            .email("test@icloud.com")
            .password("1234")
            .name("테스트")
            .phone("010-1234-1234")
            .imageUrl("https://test.com")
            .role(JOB_SEEKER)
            .build();

    static Member company = Member.builder()
            .id(1L)
            .email("test@naver.com")
            .phone("010-1234-1234")
            .name("테스트")
            .password("1234")
            .businessNumber("123-45-67890")
            .role(MemberRole.COMPANY)
            .build();
    static JobPosting jobPosting = JobPosting.builder()
            .id(1L)
            .title("테스트 공고")
            .member(company)
            .content("테스트입니다")
            .imageUrl("테스트입니다")
            .position("대리")
            .reward(50000L)
            .techStacks(Arrays.asList("Java", "Spring"))
            .build();

    @Test
    @DisplayName("구직 신청 - 성공")
    public void addApplication_success() {
        // Given
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));

        when(jobPostingRepository.findById(jobPosting.getId())).thenReturn(Optional.of(jobPosting));

        when(applicationRepository.findByApplicantAndJobPosting(member, jobPosting))
                .thenReturn(Optional.empty());

        // When
        JobApplicationDto jobApplicationDto = jobApplicationService.addApplication(
                member.getId(), jobPosting.getId());

        // Then
        assertThat(jobApplicationDto).isNotNull();
    }

    @Test
    @DisplayName("구직 신청 - 실패 (구직자 없음)")
    public void addApplication_Fail_JobSeekerNotFound() {
        // Given
        Long memberId = 1L;
        Long jobPostingId = 2L;

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> jobApplicationService.addApplication(memberId, jobPostingId))
                .isInstanceOf(CustomException.class)
                .hasMessage("존재하지 않는 구직자입니다.");
    }

    @Test
    @DisplayName("구직 신청 - 실패 (공고 없음)")
    public void addApplication_Fail_NotJobSeeker() {
        // Given
        Long jobPostingId = 2L;
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));

        // When & Then
        assertThatThrownBy(() -> jobApplicationService.addApplication(member.getId(), jobPostingId))
                .isInstanceOf(CustomException.class)
                .hasMessage("존재하지 않는 채용공고입니다.");
    }

    @Test
    @DisplayName("구직 신청 - 실패 (이미 지원한공고)")
    public void addApplication_Fail_AlreadyApplied() {
        // Given
        Long memberId = 1L;
        Long jobPostingId = 2L;

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        when(jobPostingRepository.findById(jobPostingId)).thenReturn(Optional.of(jobPosting));

        when(applicationRepository.findByApplicantAndJobPosting(member, jobPosting))
                .thenReturn(Optional.of(new JobApplication()));

        // When & Then
        assertThatThrownBy(() -> jobApplicationService.addApplication(memberId, jobPostingId))
                .isInstanceOf(CustomException.class)
                .hasMessage("이미 지원한 공고입니다.");
    }

    @Test
    public void testGetApplications() {
        // Given

        JobApplication application1 =
                JobApplication.builder()
                        .id(1L)
                        .applicant(member)
                        .jobPosting(jobPosting)
                        .build();

        JobApplication application2 =
                JobApplication.builder()
                        .id(2L)
                        .applicant(member)
                        .jobPosting(jobPosting)
                        .build();

        List<JobApplication> jobApplications = Arrays.asList(application1, application2);

        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(applicationRepository.findAllByApplicant(member)).thenReturn(jobApplications);

        // When
        List<JobApplicationDto> result =
                jobApplicationService.getApplications(member.getId());

        // Then
        assertThat(result).isNotNull();
    }
}
