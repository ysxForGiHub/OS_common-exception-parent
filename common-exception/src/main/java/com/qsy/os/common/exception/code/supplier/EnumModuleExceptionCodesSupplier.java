package com.qsy.os.common.exception.code.supplier;

import com.qsy.os.common.exception.code.IModuleExceptionCode;
import com.qsy.os.common.exception.code.source.ModuleExceptionCodeEnum;

import java.util.Arrays;
import java.util.List;

public class EnumModuleExceptionCodesSupplier implements IModuleExceptionCodesSupplier {

    @Override
    public List<IModuleExceptionCode> supply() {
        return Arrays.asList(ModuleExceptionCodeEnum.values());
    }
}
