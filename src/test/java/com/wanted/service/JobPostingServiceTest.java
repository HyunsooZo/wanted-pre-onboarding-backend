package com.wanted.service;

import com.wanted.dto.JobPostingModificationRequest;
import com.wanted.dto.jobposting.JobPostingDetailDto;
import com.wanted.dto.jobposting.JobPostingDto;
import com.wanted.dto.jobposting.JobPostingRegistrationRequest;
import com.wanted.exception.CustomException;
import com.wanted.exception.ErrorCode;
import com.wanted.model.Company;
import com.wanted.model.JobPosting;
import com.wanted.repository.CompanyRepository;
import com.wanted.repository.JobPostingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("채용공고 관련 테스트코드")
public class JobPostingServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private JobPostingRepository jobPostingRepository;

    private JobPostingService jobPostingService;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        jobPostingService = new JobPostingService(jobPostingRepository, companyRepository);
    }

    static Company company = Company.builder()
            .id(1L)
            .email("test@naver.com")
            .phone("010-1234-1234")
            .address("서울시 강남구")
            .name("테스트")
            .password("1234")
            .businessNumber("123-45-67890")
            .build();

    static JobPosting targetJobPosting = JobPosting.builder()
            .id(1L)
            .title("테스트 공고")
            .company(company)
            .content("테스트입니다")
            .imageUrl("테스트입니다")
            .position("대리")
            .reward(50000L)
            .techStacks(Arrays.asList("Java", "Spring"))
            .build();

    @Test
    @DisplayName("채용공고 등록 - 성공")
    public void addJobPosting_Success() {
        // Given
        JobPostingRegistrationRequest requestDto =
                JobPostingRegistrationRequest.builder()
                        .companyEmail("test@naver.com")
                        .content("테스트입니다")
                        .imageUrl("테스트입니다")
                        .position("대리")
                        .reward(50000L)
                        .techStacks(Arrays.asList("Java", "Spring"))
                        .build();

        Company company = new Company();
        when(companyRepository.findByEmail("test@naver.com"))
                .thenReturn(Optional.of(company));

        // When
        jobPostingService.addJobPosting(requestDto);

        // Then
        verify(jobPostingRepository, times(1))
                .save(any(JobPosting.class));
    }

    @Test
    @DisplayName("채용공고 등록 - 없는 기업")
    public void addJobPosting_Failure_CompanyNotFound() {
        // Given
        JobPostingRegistrationRequest requestDto =
                JobPostingRegistrationRequest.builder()
                        .companyEmail("test@naver.com")
                        .content("테스트입니다")
                        .imageUrl("테스트입니다")
                        .position("대리")
                        .reward(50000L)
                        .techStacks(Arrays.asList("Java", "Spring"))
                        .build();

        when(companyRepository.findByEmail("test@naver.com"))
                .thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> jobPostingService.addJobPosting(requestDto))
                .isInstanceOf(CustomException.class)
                .hasMessage("존재하지 않는 회사입니다.");
    }

    @Test
    @DisplayName("채용공고 리스트 조회 - 성공")
    public void testGetJobPostings_success() {
        // Given
        JobPosting jobPosting1 = JobPosting.builder()
                .id(1L)
                .techStacks(Arrays.asList("Java", "Spring"))
                .company(company)
                .content("테스트입니다")
                .imageUrl("테스트입니다")
                .position("대리")
                .reward(50000L)
                .build();

        JobPosting jobPosting2 = JobPosting.builder()
                .id(2L)
                .company(company)
                .content("테스트입니다")
                .imageUrl("테스트입니다")
                .position("대리")
                .reward(50000L)
                .techStacks(Arrays.asList("Python", "C++"))
                .build();

        List<JobPosting> jobPostingList = Arrays.asList(jobPosting1, jobPosting2);

        when(jobPostingRepository.findAll()).thenReturn(jobPostingList);

        // When
        List<JobPostingDto> result = jobPostingService.getJobPostings();

        // Then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getTechStacks())
                .isEqualTo(Arrays.asList("Java", "Spring"));
        assertThat(result.get(1).getTechStacks())
                .isEqualTo(Arrays.asList("Python", "C++"));
    }

    @Test
    @DisplayName("채용공고 상세 정보 조회 - 성공")
    public void testGetJobPostingDetails_success() {
        // Given
        Long companyId = 1L;

        List<JobPosting> jobPostings = new ArrayList<>();
        JobPosting relatedJobPosting1 = JobPosting.builder()
                .id(9L).title("테스트2").build();
        jobPostings.add(relatedJobPosting1);

        JobPosting relatedJobPosting2 = JobPosting.builder()
                .id(8L).title("테스트3").build();
        jobPostings.add(relatedJobPosting2);

        when(jobPostingRepository.findById(companyId)).thenReturn(Optional.of(targetJobPosting));
        when(jobPostingRepository.findByCompany(targetJobPosting.getCompany())).thenReturn(jobPostings);
        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));

        // When
        JobPostingDetailDto result = jobPostingService.getJobPostingDetails(companyId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getJobPostingDto().getCompanyName())
                .isEqualTo(targetJobPosting.getCompany().getName());
        assertThat(result.getRelations().size()).isEqualTo(2);
        assertThat(result.getRelations().get(0).getTitle()).isEqualTo("테스트2");
    }

    @Test
    @DisplayName("채용공고 상세 정보 조회 - 없는회사")
    public void testGetJobPostingDetails_CompanyNotFound() {
        // Given
        Long nonExistentCompanyId = 99L;

        List<JobPosting> jobPostings = new ArrayList<>();
        JobPosting relatedJobPosting1 = JobPosting.builder()
                .id(9L).title("테스트2").build();
        jobPostings.add(relatedJobPosting1);

        JobPosting relatedJobPosting2 = JobPosting.builder()
                .id(8L).title("테스트3").build();
        jobPostings.add(relatedJobPosting2);

        when(jobPostingRepository.findById(nonExistentCompanyId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(()-> jobPostingService.getJobPostingDetails(nonExistentCompanyId))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.JOB_POSTING_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("채용공고 정보 수정 - 성공")
    void testModifyJobPosting_success() {
        // Given
        JobPostingModificationRequest modificationRequestDto =
                JobPostingModificationRequest.builder()
                        .content("변경입니다")
                        .imageUrl("변경입니다")
                        .reward(1000L)
                        .position("")
                        .title(null)
                        .techStacks(Arrays.asList("변경1", "변경2"))
                        .build();

        when(jobPostingRepository.findById(targetJobPosting.getId()))
                .thenReturn(Optional.of(targetJobPosting));

        // When
        jobPostingService.modifyJobPosting(
                targetJobPosting.getId(), modificationRequestDto
        );

        // Then
        assertThat(targetJobPosting.getContent()).isEqualTo("변경입니다");
        assertThat(targetJobPosting.getImageUrl()).isEqualTo("변경입니다");
        assertThat(targetJobPosting.getReward()).isEqualTo(1000);
        assertThat(targetJobPosting.getPosition()).isEqualTo("대리");
        assertThat(targetJobPosting.getTechStacks()).isEqualTo(Arrays.asList("변경1", "변경2"));
        verify(jobPostingRepository, times(1)).save(targetJobPosting);
    }

    @Test
    @DisplayName("채용공고 삭제 - 성공")
    public void testDeleteJobPosting() {
        // Given
        when(jobPostingRepository.findById(targetJobPosting.getId()))
                .thenReturn(Optional.of(targetJobPosting));

        // When
        String deletedImageUrl =
                jobPostingService.deleteJobPosting(targetJobPosting.getId());

        // Then
        verify(jobPostingRepository).delete(targetJobPosting);
        assertThat(deletedImageUrl).isEqualTo(targetJobPosting.getImageUrl());
    }
}