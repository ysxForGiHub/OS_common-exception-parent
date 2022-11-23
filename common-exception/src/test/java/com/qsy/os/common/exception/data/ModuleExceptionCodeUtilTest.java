package com.qsy.os.common.exception.data;

import cn.hutool.core.util.RandomUtil;
import com.qsy.os.common.exception.code.IModuleExceptionCode;
import com.qsy.os.common.exception.code.ModuleExceptionCodeUtil;
import com.qsy.os.common.exception.code.supplier.IModuleExceptionCodesSupplier;
import com.qsy.os.common.exception.exception.DuplicateExceptionCodeWithinDifferentFamiliesException;
import com.qsy.os.common.exception.exception.DuplicateModuleIdException;
import com.qsy.os.common.exception.exception.ExceptionCodeAbsentException;
import com.qsy.os.common.exception.exception.ExceptionCodeInvalidException;
import com.qsy.os.common.exception.fixture.IModuleExceptionCodesSupplierFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ModuleExceptionCodeUtilTest {

    @Nested
    class FindModuleExceptionCodeTest {

        @AfterEach
        void tearDown() {
            ModuleExceptionCodeUtil.clearCache();
        }

        @Test
        void should_throw_duplicate_module_id_exception_given_same_module_id_without_parent() {
            String moduleId = RandomUtil.randomString(RandomUtil.BASE_CHAR.toUpperCase(),5);
            IModuleExceptionCodesSupplier exceptionCodesSupplierWithDuplicateModuleId =
                IModuleExceptionCodesSupplierFixture.givenWithDoubleDuplicateModuleId(moduleId);

            DuplicateModuleIdException duplicateModuleIdException = assertThrows(DuplicateModuleIdException.class,
                () -> ModuleExceptionCodeUtil
                    .findModuleExceptionCode(exceptionCodesSupplierWithDuplicateModuleId, null));

            List<Map.Entry<String, List<IModuleExceptionCode>>> duplicateFullModuleIdToExceptionCodeMaps =
                duplicateModuleIdException.getDuplicateFullModuleIdToExceptionCodes();

            assertThat(duplicateFullModuleIdToExceptionCodeMaps).hasSize(1);
            Map.Entry<String, List<IModuleExceptionCode>> duplicateFullModuleIdToExceptionCodes =
                duplicateFullModuleIdToExceptionCodeMaps.get(0);
            assertThat(duplicateFullModuleIdToExceptionCodes.getKey()).isEqualTo(moduleId);
        }

        @Test
        void should_throw_duplicate_module_id_exception_given_same_module_id_with_parent() {
            String parent = "A" + RandomUtil.randomString(RandomUtil.BASE_CHAR.toUpperCase(),4);
            String moduleId = "B" + RandomUtil.randomString(RandomUtil.BASE_CHAR.toUpperCase(),4);
            IModuleExceptionCodesSupplier exceptionCodesSupplierWithDuplicateModuleId =
                IModuleExceptionCodesSupplierFixture.givenWithDoubleDuplicateModuleId(parent, moduleId);

            DuplicateModuleIdException duplicateModuleIdException = assertThrows(DuplicateModuleIdException.class,
                () -> ModuleExceptionCodeUtil
                    .findModuleExceptionCode(exceptionCodesSupplierWithDuplicateModuleId, null));

            List<Map.Entry<String, List<IModuleExceptionCode>>> duplicateFullModuleIdToExceptionCodeMaps =
                duplicateModuleIdException.getDuplicateFullModuleIdToExceptionCodes();

            assertThat(duplicateFullModuleIdToExceptionCodeMaps).hasSize(1);
            Map.Entry<String, List<IModuleExceptionCode>> duplicateFullModuleIdToExceptionCodes =
                duplicateFullModuleIdToExceptionCodeMaps.get(0);
            assertThat(duplicateFullModuleIdToExceptionCodes.getKey()).isEqualTo(parent.concat(moduleId));
        }

        @Test
        void should_throw_duplicate_exception_code_within_different_families_exception_without_parent() {
            String exceptionCode = RandomUtil.randomString(RandomUtil.BASE_CHAR.toUpperCase(),2);
            IModuleExceptionCodesSupplier exceptionCodesSupplierWithDuplicateModuleId =
                IModuleExceptionCodesSupplierFixture.givenWithDoubleDuplicateExceptionCode(exceptionCode);

            DuplicateExceptionCodeWithinDifferentFamiliesException
                duplicateExceptionCodeWithinDifferentFamiliesException =
                assertThrows(DuplicateExceptionCodeWithinDifferentFamiliesException.class,
                    () -> ModuleExceptionCodeUtil
                        .findModuleExceptionCode(exceptionCodesSupplierWithDuplicateModuleId, null));

            List<Map.Entry<String, HashSet<IModuleExceptionCode>>>
                duplicateExceptionCodeWithinDifferentFamiliesExceptionCodeToEnumEntries =
                duplicateExceptionCodeWithinDifferentFamiliesException
                .getDuplicateExceptionCodeWithinDifferentFamiliesExceptionCodeToEnumEntries();

            assertThat(duplicateExceptionCodeWithinDifferentFamiliesExceptionCodeToEnumEntries).hasSize(1);
            Map.Entry<String, HashSet<IModuleExceptionCode>> duplicateExceptionCodeToExceptionCodes =
                duplicateExceptionCodeWithinDifferentFamiliesExceptionCodeToEnumEntries.get(0);
            assertThat(duplicateExceptionCodeToExceptionCodes.getKey()).isEqualTo(exceptionCode);
        }

        @Test
        void should_throw_duplicate_exception_code_within_different_families_exception_with_different_parent() {
            String parentExceptionCode = "A" + RandomUtil.randomString(RandomUtil.BASE_CHAR.toUpperCase(), 1);
            String exceptionCode = "B" + RandomUtil.randomString(RandomUtil.BASE_CHAR.toUpperCase(),1);
            IModuleExceptionCodesSupplier exceptionCodesSupplierWithDuplicateModuleId =
                IModuleExceptionCodesSupplierFixture
                    .givenWithDoubleDuplicateExceptionCodeAndDifferentRootParent(parentExceptionCode, exceptionCode);

            DuplicateExceptionCodeWithinDifferentFamiliesException
                duplicateExceptionCodeWithinDifferentFamiliesException =
                assertThrows(DuplicateExceptionCodeWithinDifferentFamiliesException.class,
                    () -> ModuleExceptionCodeUtil
                        .findModuleExceptionCode(exceptionCodesSupplierWithDuplicateModuleId, null));

            List<Map.Entry<String, HashSet<IModuleExceptionCode>>>
                duplicateExceptionCodeWithinDifferentFamiliesExceptionCodeToEnumEntries =
                duplicateExceptionCodeWithinDifferentFamiliesException
                .getDuplicateExceptionCodeWithinDifferentFamiliesExceptionCodeToEnumEntries();

            assertThat(duplicateExceptionCodeWithinDifferentFamiliesExceptionCodeToEnumEntries).hasSize(2);

            Set<String> duplicateExceptionCode = duplicateExceptionCodeWithinDifferentFamiliesExceptionCodeToEnumEntries.stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

            assertThat(duplicateExceptionCode).contains(parentExceptionCode, exceptionCode);

            assertThat(duplicateExceptionCodeWithinDifferentFamiliesException.getAvailableExceptionCodes())
                .hasSize(26 * 26 - 2);
            assertThat(duplicateExceptionCodeWithinDifferentFamiliesException.getAvailableExceptionCodes())
                .doesNotContain(parentExceptionCode, exceptionCode);

            System.err.println(duplicateExceptionCodeWithinDifferentFamiliesException.getMessage());
        }

        @Test
        void should_not_throw_exception_given_duplicate_exception_code_with_same_parent() {
            String parentExceptionCode = RandomUtil.randomString(RandomUtil.BASE_CHAR.toUpperCase(),2);
            String exceptionCode = RandomUtil.randomString(RandomUtil.BASE_CHAR.toUpperCase(),2);
            IModuleExceptionCodesSupplier exceptionCodesSupplierWithDuplicateModuleId =
                IModuleExceptionCodesSupplierFixture
                    .givenWithDoubleDuplicateExceptionCode(parentExceptionCode, exceptionCode);

            ModuleExceptionCodeUtil
                .findModuleExceptionCode(exceptionCodesSupplierWithDuplicateModuleId, null);
        }

        @Test
        void should_throw_exception_code_absent_exception() {
            IModuleExceptionCodesSupplier exceptionCodesSupplier = IModuleExceptionCodesSupplierFixture
                .givenWithDoubleDuplicateExceptionCode(null, null);

            ExceptionCodeAbsentException exceptionCodeAbsentException = assertThrows(ExceptionCodeAbsentException.class,
                () -> ModuleExceptionCodeUtil.findModuleExceptionCode(exceptionCodesSupplier, null));

            assertThat(exceptionCodeAbsentException.getExceptionCodeAbsentExceptionCodes()).hasSize(2);
        }

        @Test
        void should_throw_exception_code_invalid_exception() {
            IModuleExceptionCodesSupplier exceptionCodesSupplier = IModuleExceptionCodesSupplierFixture
                .givenWithDoubleDuplicateExceptionCode(null,
                    Stream.of("az", "A", "Aa", "1", ",").findAny().get());

            ExceptionCodeInvalidException exceptionCodeInvalidException = assertThrows(ExceptionCodeInvalidException.class,
                () -> ModuleExceptionCodeUtil.findModuleExceptionCode(exceptionCodesSupplier, null));

            assertThat(exceptionCodeInvalidException.getExceptionCodeAbsentExceptionCodes()).hasSize(2);
        }
    }
}
