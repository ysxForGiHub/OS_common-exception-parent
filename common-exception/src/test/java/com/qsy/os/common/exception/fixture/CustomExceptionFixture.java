package com.qsy.os.common.exception.fixture;

import com.qsy.os.common.exception.BaseException;

public class CustomExceptionFixture {

    public static CustomException given(ResponseEnumFixture.AResponseEnum responseEnum) {
        return CustomException.of(CustomException::new, responseEnum);
    }


    public static class CustomException extends BaseException {

    }
}
