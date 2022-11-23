package com.qsy.os.common.exception.fixture;

import com.qsy.os.common.exception.BaseException;
import com.qsy.os.common.exception.IExceptionCode;

public class BaseExceptionFixture {

    public static BaseException given(IExceptionCode responseEnum) {
        return BaseException.of(responseEnum);
    }

    public static BaseException given(IExceptionCode responseEnum, Object... args) {
        return BaseException.of(responseEnum, args);
    }

    public static BaseException givenWithData(IExceptionCode responseEnum, Object data) {
        return BaseException.ofWithData(responseEnum, data);
    }

    public static BaseException givenWithData(IExceptionCode responseEnum, Object data, Object... args) {
        return BaseException.ofWithData(responseEnum, data, args);
    }

    public static BaseException givenWithCause(IExceptionCode responseEnum, Throwable cause) {
        return BaseException.ofWithCause(responseEnum, cause);
    }

    public static BaseException givenWithCause(IExceptionCode responseEnum, Throwable cause, Object... args) {
        return BaseException.ofWithCause(responseEnum, cause, args);
    }

    public static BaseException given(IExceptionCode responseEnum, Object data, Throwable cause) {
        return BaseException.ofWithCauseAndData(responseEnum, cause, data);
    }

    public static BaseException given(IExceptionCode responseEnum, Object data, Throwable cause, Object... args) {
        return BaseException.ofWithCauseAndData(responseEnum, cause, data, args);
    }

    public static BaseException given() {
        return BaseException.of(ResponseEnumFixture.AResponseEnum.A);
    }
}
