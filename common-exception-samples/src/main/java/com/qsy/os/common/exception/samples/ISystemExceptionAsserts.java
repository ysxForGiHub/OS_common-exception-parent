package com.qsy.os.common.exception.samples;

import com.qsy.os.common.exception.IExceptionCodeAsserts;
import com.qsy.os.common.exception.annotation.ExceptionSource;

import static com.qsy.os.common.exception.ExceptionSourceEnum.SYSTEM_EXCEPTION;

@SuppressWarnings("unchecked")
@ExceptionSource(SYSTEM_EXCEPTION)
public interface ISystemExceptionAsserts extends IExceptionCodeAsserts {

    @Override
    default SystemException newE(Object... args) {
        return SystemException.of(SystemException::new, this, args);
    }

    @Override
    default SystemException newEWithCause(Throwable cause, Object... args) {
        return SystemException.ofWithCause(SystemException::new, this, cause, args);
    }

    @Override
    default SystemException newEWithData(Object data, Object... args) {
        return SystemException.ofWithData(SystemException::new, this, data, args);
    }

    @Override
    default SystemException newEWithCauseAndData(Throwable cause, Object data, Object... args) {
        return SystemException.ofWithCauseAndData(SystemException::new, this, cause, data, args);
    }
}
