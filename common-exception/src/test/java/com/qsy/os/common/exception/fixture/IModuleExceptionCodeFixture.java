package com.qsy.os.common.exception.fixture;

import com.qsy.os.common.exception.code.IModuleExceptionCode;

public class IModuleExceptionCodeFixture {

    public static IModuleExceptionCode given(String moduleId) {
        return given(null, moduleId, null);
    }

    public static <T extends IModuleExceptionCode> IModuleExceptionCode given(T parent, String moduleId) {
        return given(parent, moduleId, null);
    }

    public static <T extends IModuleExceptionCode> IModuleExceptionCode given(
        T parent, String moduleId, String exceptionCode) {
        return new IModuleExceptionCode() {
            @SuppressWarnings("unchecked")
            @Override
            public <E extends IModuleExceptionCode> E getParent() {
                return (E) parent;
            }

            @Override
            public String getModuleId() {
                return moduleId;
            }

            @Override
            public String getExceptionCode() {
                return exceptionCode;
            }
        };
    }
}
