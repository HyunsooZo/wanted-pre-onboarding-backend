package com.wanted.service;

import com.wanted.dto.JobPostingDto;
import com.wanted.exception.CustomException;
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

    @Test
    @DisplayName("채용공고 등록 - 성공")
    public void addJobPosting_Success() {
        // Given
        JobPostingDto.PostingRequest requestDto =
                JobPostingDto.PostingRequest.builder()
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
        JobPostingDto.PostingRequest requestDto =
                JobPostingDto.PostingRequest.builder()
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
        Company company = Company.builder()
                .id(1L)
                .email("test@naver.com")
                .phone("010-1234-1234")
                .address("서울시 강남구")
                .name("테스트")
                .password("1234")
                .businessNumber("123-45-67890")
                .build();

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


}
