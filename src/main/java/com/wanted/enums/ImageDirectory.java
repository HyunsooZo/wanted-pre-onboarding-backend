package com.wanted.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ImageDirectory {
    JOB_POSTING("job-posting");
    private final String string;
}
