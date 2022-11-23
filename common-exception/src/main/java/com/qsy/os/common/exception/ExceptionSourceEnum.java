package com.qsy.os.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ExceptionSourceEnum {

    SYSTEM_EXCEPTION("S", "exception from inner system, but not caused by user's input."),
    USER_EXCEPTION("U", "exception from user's input, business exceptions"),
    EXTERNAL_EXCEPTION("E", "");

    private final String code;
    private final String description;


    @Nullable
    public static ExceptionSourceEnum parseFromCode(String exceptionSourceCode) {
        return Arrays.stream(values())
                .filter(exceptionSourceEnum -> exceptionSourceEnum.getCode().equals(exceptionSourceCode))
                .findFirst()
                .orElse(null);
    }
}
