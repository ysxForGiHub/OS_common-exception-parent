package com.qsy.os.common.exception.code.source;

import com.qsy.os.common.exception.code.IModuleExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ModuleExceptionCodeEnum implements IModuleExceptionCode {

    // just for samples
    SAMPLES(null, "com.qsy.os.common-exception-samples", "--");

    private final ModuleExceptionCodeEnum parent;
    private final String moduleId;
    private final String exceptionCode;
}
