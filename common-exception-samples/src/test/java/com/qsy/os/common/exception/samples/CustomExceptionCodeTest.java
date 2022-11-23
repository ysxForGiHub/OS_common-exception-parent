package com.qsy.os.common.exception.samples;

import org.junit.jupiter.api.Test;

import static com.qsy.os.common.exception.code.source.ModuleExceptionCodeEnum.SAMPLES;
import static com.qsy.os.common.exception.samples.CustomExceptionCode.UNKNOWN;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomExceptionCodeTest {

    @Test
    void assert_unknown_exception_code() {
        assertThat(UNKNOWN.getCode()).isEqualTo("S" + SAMPLES.getExceptionCode() + "0001");
    }
}
