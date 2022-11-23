package com.qsy.os.common.exception.samples;

import com.qsy.os.common.exception.BaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SystemExceptionAssertsTest {

    private ISystemExceptionAsserts commonBaseExceptionAsserts;

    @BeforeEach
    void setUp() {
        commonBaseExceptionAsserts = new ISystemExceptionAsserts() {
            @Override
            public String getCode() {
                return "";
            }

            @Override
            public String getMessage() {
                return "";
            }
        };
    }

    @Test
    void should_new_exception_successfully() {
        final Object arg = new Object();
        SystemException commonBaseException = commonBaseExceptionAsserts.newE(arg);
        assertThat(commonBaseException).isEqualTo(SystemException.of(SystemException::new, commonBaseExceptionAsserts, arg));
    }

    @Test
    void should_new_exception_with_cause_successfully() {
        final Throwable cause = new RuntimeException();
        final Object arg = new Object();
        BaseException commonBaseException = commonBaseExceptionAsserts.newEWithCause(cause, arg);
        assertThat(commonBaseException).isEqualTo(SystemException.ofWithCause(SystemException::new, commonBaseExceptionAsserts, null, arg));
        assertThat(commonBaseException.getCause()).isEqualTo(cause);
    }

    @Test
    void should_new_exception_with_data_successfully() {
        final Object data = new Object();
        final Object arg = new Object();
        BaseException commonBaseException = commonBaseExceptionAsserts.newEWithData(data, arg);
        assertThat(commonBaseException).isEqualTo(SystemException.ofWithData(SystemException::new, commonBaseExceptionAsserts, data, arg));
    }

    @Test
    void should_new_exception_with_cause_and_data_successfully() {
        final Throwable cause = new RuntimeException();
        final Object data = new Object();
        final Object arg = new Object();
        BaseException baseException = commonBaseExceptionAsserts.newEWithCauseAndData(cause, data, arg);
        assertThat(baseException).isEqualTo(SystemException.ofWithCauseAndData(SystemException::new,commonBaseExceptionAsserts, null, data, arg));
        assertThat(baseException.getCause()).isEqualTo(cause);
    }
}
