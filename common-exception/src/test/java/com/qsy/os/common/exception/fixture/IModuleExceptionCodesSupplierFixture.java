package com.qsy.os.common.exception.fixture;

import cn.hutool.core.util.RandomUtil;
import com.qsy.os.common.exception.code.IModuleExceptionCode;
import com.qsy.os.common.exception.code.supplier.IModuleExceptionCodesSupplier;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.qsy.os.common.exception.fixture.IModuleExceptionCodeFixture.given;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IModuleExceptionCodesSupplierFixture {

    public static IModuleExceptionCodesSupplier givenWithDoubleDuplicateModuleId(String duplicateModuleId) {
        return givenWithDoubleDuplicateModuleId(null, duplicateModuleId);
    }

    public static IModuleExceptionCodesSupplier givenWithDoubleDuplicateModuleId(String parentModuleId,
                                                                                 String duplicateModuleId) {
        return () -> {
            IModuleExceptionCode parent = Optional.ofNullable(parentModuleId)
                .map(nonNullModuleId -> IModuleExceptionCodeFixture.given(
                    null,
                    parentModuleId,
                    RandomUtil.randomString(RandomUtil.BASE_CHAR.toUpperCase(), 2)))
                .orElse(null);
            List<IModuleExceptionCode> moduleExceptionCodes = new ArrayList<>(
                Arrays.asList(
                    IModuleExceptionCodeFixture.given(parent, duplicateModuleId, RandomUtil.randomString(RandomUtil.BASE_CHAR.toUpperCase(), 2)),
                    IModuleExceptionCodeFixture.given(parent, duplicateModuleId, RandomUtil.randomString(RandomUtil.BASE_CHAR.toUpperCase(), 2))
                ));
            Optional.ofNullable(parent).ifPresent(moduleExceptionCodes::add);
            return moduleExceptionCodes;
        };
    }

    public static IModuleExceptionCodesSupplier givenWithDoubleDuplicateExceptionCode(String duplicateExceptionCode) {
        return givenWithDoubleDuplicateExceptionCode(null, duplicateExceptionCode);
    }

    public static IModuleExceptionCodesSupplier givenWithDoubleDuplicateExceptionCode(String parentExceptionCode,
                                                                                 String duplicateExceptionCode) {
        return () -> {
            IModuleExceptionCode parent = Optional.ofNullable(parentExceptionCode)
                .map(nonNullExceptionCode ->
                    IModuleExceptionCodeFixture.given(null, RandomUtil.randomString(5), nonNullExceptionCode))
                .orElse(null);
            List<IModuleExceptionCode> moduleExceptionCodes = new ArrayList<>(
                Arrays.asList(
                    IModuleExceptionCodeFixture.given(parent, "A" + RandomUtil.randomString(4), duplicateExceptionCode),
                    IModuleExceptionCodeFixture.given(parent, "B" + RandomUtil.randomString(4), duplicateExceptionCode)
                ));
            Optional.ofNullable(parent).ifPresent(moduleExceptionCodes::add);
            return moduleExceptionCodes;
        };
    }

    public static IModuleExceptionCodesSupplier givenWithDoubleDuplicateExceptionCodeAndDifferentRootParent(
        String parentExceptionCode, String duplicateExceptionCode) {
        return () -> {
            IModuleExceptionCode rootParent = IModuleExceptionCodeFixture.given(null, RandomUtil.randomString(5), parentExceptionCode);
            IModuleExceptionCode anotherRootParent = IModuleExceptionCodeFixture.given(null, RandomUtil.randomString(5), parentExceptionCode);
            return new ArrayList<>(
                Arrays.asList(
                    rootParent,
                    anotherRootParent,
                    IModuleExceptionCodeFixture.given(rootParent, "A" + RandomUtil.randomString(4), duplicateExceptionCode),
                    IModuleExceptionCodeFixture.given(anotherRootParent, "B" + RandomUtil.randomString(4), duplicateExceptionCode),
                    IModuleExceptionCodeFixture.given(rootParent, "B" + RandomUtil.randomString(4), null)
                ));
        };
    }
}
