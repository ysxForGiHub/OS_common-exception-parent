package com.qsy.os.common.exception;

import cn.hutool.core.util.ReUtil;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class ExceptionSourceEnumTest {

    @Nested
    class CodeTest {

        @Test
        void should_not_duplicate() {
            assertThat(Arrays.stream(ExceptionSourceEnum.values())
                .map(ExceptionSourceEnum::getCode)
                .distinct()
                .count()).isEqualTo(ExceptionSourceEnum.values().length);
        }

        @Test
        void should_be_one_upper_alphabet() {
            Pattern pattern = Pattern.compile("^[A-Z]$");
            assertThat(Arrays.stream(ExceptionSourceEnum.values())
                .map(ExceptionSourceEnum::getCode)
                .allMatch(code -> ReUtil.isMatch(pattern, code))
            )
                .isTrue();
        }

        @Test
        void should_match_length_setting() {
            assertThat(Arrays.stream(ExceptionSourceEnum.values())
                    .map(ExceptionSourceEnum::getCode)
                    .map(String::length)
                    .allMatch(length -> ExceptionSettings.EXCEPTION_SOURCE_CODE_LENGTH == length)
            )
                    .isTrue();
        }
    }

    @Nested
    class ParseFromCodeTest {

        @Test
        void should_return_exception_source_enum_given_code_exist() {
            @SuppressWarnings("OptionalGetWithoutIsPresent")
            final String code = Arrays.stream(ExceptionSourceEnum.values())
                    .map(ExceptionSourceEnum::getCode)
                    .findAny()
                    .get();

            ExceptionSourceEnum exceptionSourceEnum = ExceptionSourceEnum.parseFromCode(code);

            assertThat(exceptionSourceEnum).isNotNull();
        }

        @Test
        void should_return_null_given_code_not_exist() {
            ExceptionSourceEnum exceptionSourceEnum = ExceptionSourceEnum.parseFromCode("");

            assertThat(exceptionSourceEnum).isNull();
        }
    }
}
