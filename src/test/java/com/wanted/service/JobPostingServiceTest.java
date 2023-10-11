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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("채용공고 관련 테스트코드")
public class JobPostingServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private JobPostingRepository jobPostingRepository;

    @InjectMocks
    private JobPostingService jobPostingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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
                        .reward(50000)
                        .techStacks(new ArrayList<>(Arrays.asList("Java", "Spring")))
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
                        .reward(50000)
                        .techStacks(new ArrayList<>(Arrays.asList("Java", "Spring")))
                        .build();

        when(companyRepository.findByEmail("test@naver.com"))
                .thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> jobPostingService.addJobPosting(requestDto))
                .isInstanceOf(CustomException.class)
                .hasMessage("존재하지 않는 회사입니다.");
    }
}
