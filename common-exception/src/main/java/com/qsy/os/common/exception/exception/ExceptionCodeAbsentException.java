package com.qsy.os.common.exception.exception;

import com.qsy.os.common.exception.code.IModuleExceptionCode;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ExceptionCodeAbsentException extends RuntimeException {
    private final List<IModuleExceptionCode> exceptionCodeAbsentExceptionCodes;

    public ExceptionCodeAbsentException(List<IModuleExceptionCode> exceptionCodeAbsentExceptionCodes) {
        super(getExceptionCodeAbsentExceptionMessage(exceptionCodeAbsentExceptionCodes));
        this.exceptionCodeAbsentExceptionCodes = exceptionCodeAbsentExceptionCodes;
    }

    private static String getExceptionCodeAbsentExceptionMessage(
        List<IModuleExceptionCode> exceptionCodeAbsentExceptionCodes) {
        final String template = "    - [%s]";
        return "can not found exception code in following exception code:\n".concat(
            exceptionCodeAbsentExceptionCodes.stream()
                .map(exceptionCode -> String.format(template, exceptionCode.getSimpleQualifiedExceptionCodeName()))
                .collect(Collectors.joining("\n"))
        );
    }


}
