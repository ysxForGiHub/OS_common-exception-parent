package com.qsy.os.common.exception.processor;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.qsy.os.common.exception.code.ModuleExceptionCodeUtil.findModuleExceptionCode;
import static org.assertj.core.api.Assertions.assertThat;

public class ExceptionProcessorConstTest {

    @Nested
    class ProcessorExceptionCodesSupplierTest {

        @Test
        void should_get_excepted_module_exception_code() {
            assertThat(findModuleExceptionCode(ExceptionProcessorConst.PROCESSOR_EXCEPTION_CODES_SUPPLIER,
                "com.qsy.os.common-exception-samples")).hasValue("--");
        }
    }
}
