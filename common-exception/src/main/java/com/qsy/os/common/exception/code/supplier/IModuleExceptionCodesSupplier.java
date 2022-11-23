package com.qsy.os.common.exception.code.supplier;

import com.qsy.os.common.exception.code.IModuleExceptionCode;

import java.util.List;

public interface IModuleExceptionCodesSupplier {

    List<IModuleExceptionCode> supply();
}
