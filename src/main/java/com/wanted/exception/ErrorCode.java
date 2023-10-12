package com.wanted.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    // 공통 Exception
    UNDEFINED_EXCEPTION(HttpStatus.BAD_REQUEST, "알 수 없는 예외가 발생했습니다."),

    // 회사 Exception
    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회사입니다."),

    // 채용공고 Exception
    JOB_POSTING_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 채용공고입니다.");

    private final HttpStatus status;
    private final String message;
}