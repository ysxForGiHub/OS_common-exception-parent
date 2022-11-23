package com.qsy.os.common.exception.code;

import java.util.Optional;

public interface IModuleExceptionCode {
    <E extends IModuleExceptionCode> E getParent();

    String getModuleId();

    String getExceptionCode();

    default String getSimpleQualifiedExceptionCodeName() {
        return this.getClass().getSimpleName().concat(".").concat(this.toString());
    }

    static String getFullModuleId(IModuleExceptionCode moduleExceptionCode) {
        if (moduleExceptionCode.getParent() == null) {
            return moduleExceptionCode.getModuleId();
        }
        return getFullModuleId(moduleExceptionCode.getParent()) + moduleExceptionCode.getModuleId();
    }

    static IModuleExceptionCode getRoot(IModuleExceptionCode aEnum) {
        IModuleExceptionCode root = null;
        do {
            if (aEnum.getParent() == null) {
                root = aEnum;
            }
            aEnum = aEnum.getParent();
        } while (aEnum != null);
        return root;
    }

    default Optional<String> findExceptionCode() {
        IModuleExceptionCode aEnum = this;
        String exceptionCode;
        do {
            exceptionCode = aEnum.getExceptionCode();
            aEnum = aEnum.getParent();
        } while (aEnum != null && exceptionCode == null);
        return Optional.ofNullable(exceptionCode);
    }
}
