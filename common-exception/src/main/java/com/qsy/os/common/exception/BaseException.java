package com.qsy.os.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.function.Supplier;

import static lombok.AccessLevel.PROTECTED;

@Getter
@SuppressWarnings("unchecked")
@NoArgsConstructor(access = PROTECTED)
public class BaseException extends RuntimeException {

    protected IExceptionCode responseEnum;

    protected Object[] args;

    protected String message;

    protected Object data;

    public static <T extends BaseException> T of(IExceptionCode responseEnum, Object... args) {
        return (T) of(BaseException::new, responseEnum, args);
    }

    public static <T extends BaseException> T ofWithCause(
        IExceptionCode responseEnum, Throwable cause, Object... args) {
        return (T) ofWithCause(BaseException::new, responseEnum, cause, args);
    }

    public static <T extends BaseException> T ofWithData(
        IExceptionCode responseEnum, Object data, Object... args) {
        return (T) ofWithData(BaseException::new, responseEnum, data, args);
    }

    public static <T extends BaseException> T ofWithCauseAndData(
        IExceptionCode responseEnum, Throwable cause, Object data, Object... args) {
        return (T) ofWithCauseAndData(BaseException::new, responseEnum, cause, data, args);
    }

    public static <T extends BaseException> T of(
        Supplier<T> exceptionSupplier, IExceptionCode responseEnum) {
        T baseException = exceptionSupplier.get();
        baseException.responseEnum = responseEnum;
        baseException.message = responseEnum.getMessage();
        return baseException;
    }

    public static <T extends BaseException> T of(
        Supplier<T> exceptionSupplier, IExceptionCode responseEnum, Object... args) {
        T baseException = exceptionSupplier.get();
        baseException.responseEnum = responseEnum;
        baseException.args = args;
        baseException.message = MessageFormat.format(responseEnum.getMessage(), args);
        return baseException;
    }

    public static <T extends BaseException> T ofWithCause(
        Supplier<T> exceptionSupplier, IExceptionCode responseEnum, Throwable cause, Object... args) {
        T baseException = of(exceptionSupplier, responseEnum, args);
        baseException.initCause(cause);
        return baseException;
    }

    public static <T extends BaseException> T ofWithData(
        Supplier<T> exceptionSupplier, IExceptionCode responseEnum, Object data, Object... args) {
        T baseException = of(exceptionSupplier, responseEnum, args);
        baseException.data = data;
        return baseException;
    }

    public static <T extends BaseException> T ofWithCauseAndData(
        Supplier<T> exceptionSupplier,
        IExceptionCode responseEnum, Throwable cause, Object data, Object... args) {
        T baseException = of(exceptionSupplier, responseEnum, args);
        baseException.data = data;
        baseException.initCause(cause);
        return baseException;
    }

    public <T> T doThrow() {
        throw this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseException that = (BaseException) o;
        return Objects.equals(responseEnum, that.responseEnum)
            && Objects.equals(message, that.message)
            && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseEnum, message, data);
    }

    @Nullable
    public ExceptionSourceEnum getExceptionSource() {
        String code = responseEnum.getCode();
        String exceptionSourceCode = code.substring(0, ExceptionSettings.EXCEPTION_SOURCE_CODE_LENGTH);
        return ExceptionSourceEnum.parseFromCode(exceptionSourceCode);
    }
}
