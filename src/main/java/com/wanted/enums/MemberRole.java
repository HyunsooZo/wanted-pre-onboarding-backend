package com.wanted.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberRole {
    COMPANY("ROLE_COMPANY"),
    JOB_SEEKER("ROLE_JOB_SEEKER");
    private final String string;
}
