package com.qsy.os.common.exception;

import cn.hutool.core.util.RandomUtil;
import com.qsy.os.common.exception.fixture.BaseExceptionFixture;
import com.qsy.os.common.exception.fixture.CustomExceptionFixture;
import com.qsy.os.common.exception.fixture.ResponseEnumFixture;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseExceptionTest {

    @Test
    void should_of_successfully() {
        ResponseEnumFixture.AResponseEnum responseEnum = ResponseEnumFixture.AResponseEnum.ARG;
        final String arg = RandomUtil.randomString(5);
        final Object data = new Object();
        final Throwable cause = new RuntimeException();
        BaseException baseException = BaseException.ofWithCauseAndData(responseEnum, cause, data, arg);
        assertThat(baseException.getResponseEnum()).isEqualTo(responseEnum);
        assertThat(baseException.getArgs()).hasSize(1);
        assertThat(baseException.getArgs()[0]).isEqualTo(arg);
        assertThat(baseException.getMessage()).isEqualTo(MessageFormat.format(responseEnum.getMessage(), arg));
        assertThat(baseException.getCause()).isEqualTo(cause);
        assertThat(baseException.getData()).isEqualTo(data);
    }

    @Nested
    class EqualsTest {

        @Test
        void should_return_true() {
            ResponseEnumFixture.AResponseEnum responseEnum = ResponseEnumFixture.AResponseEnum.A;

            BaseException baseException = BaseExceptionFixture.given(responseEnum);
            //noinspection EqualsWithItself
            assertThat(baseException.equals(baseException)).isTrue();

            BaseException anotherBaseException = BaseExceptionFixture.given(responseEnum);
            assertThat(baseException.equals(anotherBaseException)).isTrue();

            final Object data = new Object();
            baseException = BaseExceptionFixture.givenWithData(responseEnum, data);
            anotherBaseException = BaseExceptionFixture.givenWithData(responseEnum, data);
            assertThat(baseException.equals(anotherBaseException)).isTrue();

            final Throwable cause = new RuntimeException();
            final Throwable anotherCause = new RuntimeException();
            baseException = BaseExceptionFixture.givenWithCause(responseEnum, cause);
            anotherBaseException = BaseExceptionFixture.givenWithCause(responseEnum, anotherCause);
            assertThat(baseException).isEqualTo(anotherBaseException);

            baseException = BaseExceptionFixture.given(responseEnum, data, cause);
            anotherBaseException = BaseExceptionFixture.given(responseEnum, data, anotherCause);
            assertThat(baseException).isEqualTo(anotherBaseException);

            responseEnum = ResponseEnumFixture.AResponseEnum.ARG;

            baseException = BaseExceptionFixture.given(responseEnum, data, cause, "A");
            anotherBaseException = BaseExceptionFixture.given(responseEnum, data, anotherCause, "A");
            assertThat(baseException).isEqualTo(anotherBaseException);

            baseException = BaseExceptionFixture.givenWithData(responseEnum, data, "A");
            anotherBaseException = BaseExceptionFixture.givenWithData(responseEnum, data, "A");
            assertThat(baseException).isEqualTo(anotherBaseException);

            baseException = BaseExceptionFixture.givenWithCause(responseEnum, cause, "A");
            anotherBaseException = BaseExceptionFixture.givenWithCause(responseEnum, anotherCause, "A");
            assertThat(baseException).isEqualTo(anotherBaseException);
        }

        @Test
        void should_return_false_given_compare_to_null() {
            ResponseEnumFixture.AResponseEnum responseEnum = ResponseEnumFixture.AResponseEnum.A;

            BaseException baseException = BaseExceptionFixture.given(responseEnum);
            //noinspection ConstantConditions
            assertThat(baseException.equals(null)).isFalse();
        }

        @Test
        void should_return_false_given_compare_to_different_class() {
            ResponseEnumFixture.AResponseEnum responseEnum = ResponseEnumFixture.AResponseEnum.A;

            BaseException baseException = BaseExceptionFixture.given(responseEnum);
            CustomExceptionFixture.CustomException customException = CustomExceptionFixture.given(responseEnum);
            assertThat(baseException.equals(customException)).isFalse();
        }

        @Test
        void should_return_false_given_different_response_enum() {
            ResponseEnumFixture.AResponseEnum aResponseEnum = ResponseEnumFixture.AResponseEnum.A;
            ResponseEnumFixture.BResponseEnum bResponseEnum = ResponseEnumFixture.BResponseEnum.A;
            BaseException baseException = BaseExceptionFixture.given(aResponseEnum);
            BaseException anotherBaseException = BaseExceptionFixture.given(bResponseEnum);
            assertThat(baseException).isNotEqualTo(anotherBaseException);
        }

        @Test
        void should_return_false_given_different_response_enum_message_arg() {
            ResponseEnumFixture.AResponseEnum aResponseEnum = ResponseEnumFixture.AResponseEnum.ARG;
            BaseException baseException = BaseExceptionFixture.given(aResponseEnum, "A");
            BaseException anotherBaseException = BaseExceptionFixture.given(aResponseEnum, "B");
            assertThat(baseException).isNotEqualTo(anotherBaseException);
        }

        @Test
        void should_return_false_given_different_data() {
            ResponseEnumFixture.AResponseEnum responseEnum = ResponseEnumFixture.AResponseEnum.A;
            final Object data = new Object();
            final Object anotherData = new Object();
            BaseException baseException = BaseExceptionFixture.givenWithData(responseEnum, data);
            BaseException anotherBaseException = BaseExceptionFixture.givenWithData(responseEnum, anotherData);
            assertThat(baseException).isNotEqualTo(anotherBaseException);
        }
    }


    @Nested
    class HashCodeTest {

        @Test
        void should_return_same_hash_code() {
            ResponseEnumFixture.AResponseEnum responseEnum = ResponseEnumFixture.AResponseEnum.A;

            BaseException baseException = BaseExceptionFixture.given(responseEnum);
            assertThat(baseException.hashCode()).isEqualTo(baseException.hashCode());

            BaseException anotherBaseException = BaseExceptionFixture.given(responseEnum);
            assertThat(baseException.hashCode()).isEqualTo(anotherBaseException.hashCode());

            final Object data = new Object();
            baseException = BaseExceptionFixture.givenWithData(responseEnum, data);
            anotherBaseException = BaseExceptionFixture.givenWithData(responseEnum, data);
            assertThat(baseException.hashCode()).isEqualTo(anotherBaseException.hashCode());

            final Throwable cause = new RuntimeException();
            final Throwable anotherCause = new RuntimeException();
            baseException = BaseExceptionFixture.givenWithCause(responseEnum, cause);
            anotherBaseException = BaseExceptionFixture.givenWithCause(responseEnum, anotherCause);
            assertThat(baseException.hashCode()).isEqualTo(anotherBaseException.hashCode());

            baseException = BaseExceptionFixture.given(responseEnum, data, cause);
            anotherBaseException = BaseExceptionFixture.given(responseEnum, data, anotherCause);
            assertThat(baseException.hashCode()).isEqualTo(anotherBaseException.hashCode());

            responseEnum = ResponseEnumFixture.AResponseEnum.ARG;

            baseException = BaseExceptionFixture.given(responseEnum, data, cause, "A");
            anotherBaseException = BaseExceptionFixture.given(responseEnum, data, anotherCause, "A");
            assertThat(baseException.hashCode()).isEqualTo(anotherBaseException.hashCode());

            baseException = BaseExceptionFixture.givenWithData(responseEnum, data, "A");
            anotherBaseException = BaseExceptionFixture.givenWithData(responseEnum, data, "A");
            assertThat(baseException.hashCode()).isEqualTo(anotherBaseException.hashCode());

            baseException = BaseExceptionFixture.givenWithCause(responseEnum, cause, "A");
            anotherBaseException = BaseExceptionFixture.givenWithCause(responseEnum, anotherCause, "A");
            assertThat(baseException.hashCode()).isEqualTo(anotherBaseException.hashCode());
        }

        @Test
        void should_return_different_hash_code_given_different_response_enum() {
            ResponseEnumFixture.AResponseEnum aResponseEnum = ResponseEnumFixture.AResponseEnum.A;
            ResponseEnumFixture.BResponseEnum bResponseEnum = ResponseEnumFixture.BResponseEnum.A;
            BaseException baseException = BaseExceptionFixture.given(aResponseEnum);
            BaseException anotherBaseException = BaseExceptionFixture.given(bResponseEnum);
            assertThat(baseException.hashCode()).isNotEqualTo(anotherBaseException.hashCode());
        }

        @Test
        void should_return_different_hash_code_given_different_response_enum_message_arg() {
            ResponseEnumFixture.AResponseEnum aResponseEnum = ResponseEnumFixture.AResponseEnum.ARG;
            ResponseEnumFixture.BResponseEnum bResponseEnum = ResponseEnumFixture.BResponseEnum.ARG;
            BaseException baseException = BaseExceptionFixture.given(aResponseEnum, "A");
            BaseException anotherBaseException = BaseExceptionFixture.given(bResponseEnum, "B");
            assertThat(baseException.hashCode()).isNotEqualTo(anotherBaseException.hashCode());
        }

        @Test
        void should_return_different_hash_code_given_different_data() {
            ResponseEnumFixture.AResponseEnum responseEnum = ResponseEnumFixture.AResponseEnum.A;
            final Object data = new Object();
            final Object anotherData = new Object();
            BaseException baseException = BaseExceptionFixture.givenWithData(responseEnum, data);
            BaseException anotherBaseException = BaseExceptionFixture.givenWithData(responseEnum, anotherData);
            assertThat(baseException.hashCode()).isNotEqualTo(anotherBaseException.hashCode());
        }
    }

    @Nested
    class GetExceptionSourceTest {

        @Test
        void should_get_exception_source_enum_successfully() {
            BaseException baseException = BaseExceptionFixture.given(ResponseEnumFixture.SystemResponseEnum.A);

            ExceptionSourceEnum exceptionSource = baseException.getExceptionSource();

            assertThat(exceptionSource).isEqualTo(ExceptionSourceEnum.SYSTEM_EXCEPTION);
        }
    }
}
