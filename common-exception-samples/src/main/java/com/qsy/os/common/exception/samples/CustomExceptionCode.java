package com.qsy.os.common.exception.samples;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionCode implements ISystemExceptionAsserts {
    UNKNOWN("0001", "A UNKNOWN SYSTEM EXCEPTION");

    private final String code;
    private final String message;
}
