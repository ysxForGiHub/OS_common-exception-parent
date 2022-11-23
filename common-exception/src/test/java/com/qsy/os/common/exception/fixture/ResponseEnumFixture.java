package com.qsy.os.common.exception.fixture;

import cn.hutool.core.util.RandomUtil;
import com.qsy.os.common.exception.ExceptionSourceEnum;
import com.qsy.os.common.exception.IExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ResponseEnumFixture {

    @Getter
    @RequiredArgsConstructor
    public enum AResponseEnum implements IExceptionCode {
        A(RandomUtil.randomString(1), RandomUtil.randomString(5)),
        B(RandomUtil.randomString(2), RandomUtil.randomString(5)),
        ARG(RandomUtil.randomString(3), "{0}");

        private final String code;
        private final String message;
    }

    @Getter
    @RequiredArgsConstructor
    public enum BResponseEnum implements IExceptionCode {
        A(RandomUtil.randomString(1), RandomUtil.randomString(5)),
        B(RandomUtil.randomString(2), RandomUtil.randomString(5)),
        ARG(RandomUtil.randomString(3), "{0}");

        private final String code;
        private final String message;
    }

    @Getter
    @RequiredArgsConstructor
    public enum SystemResponseEnum implements IExceptionCode {
        A(ExceptionSourceEnum.SYSTEM_EXCEPTION.getCode() + "001", RandomUtil.randomString(5));
        private final String code;
        private final String message;
    }
}
