package com.qsy.os.common.exception.code;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.stream.SimpleCollector;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import com.qsy.os.common.exception.code.supplier.IModuleExceptionCodesSupplier;
import com.qsy.os.common.exception.exception.DuplicateExceptionCodeWithinDifferentFamiliesException;
import com.qsy.os.common.exception.exception.DuplicateModuleIdException;
import com.qsy.os.common.exception.exception.ExceptionCodeAbsentException;
import com.qsy.os.common.exception.exception.ExceptionCodeInvalidException;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.qsy.os.common.exception.code.source.ModuleExceptionCodeEnum.SAMPLES;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

public class ModuleExceptionCodeUtil {

    private static final Map<Class<? extends IModuleExceptionCodesSupplier>, Map<String, String>>
        supplierTypeToFullModuleIdToExceptionCodes = new HashMap<>(4);

    public static void clearCache() {
        supplierTypeToFullModuleIdToExceptionCodes.clear();
    }

    public static Optional<String> findModuleExceptionCode(IModuleExceptionCodesSupplier moduleExceptionSupplier,
                                                           String fullModuleId) {
        return Optional.ofNullable(
            supplierTypeToFullModuleIdToExceptionCodes.computeIfAbsent(moduleExceptionSupplier.getClass(),
                    (supplierType) -> toFullModuleIdToExceptionCodes(moduleExceptionSupplier))
                .get(fullModuleId)
        );
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static Map<String, String> toFullModuleIdToExceptionCodes(IModuleExceptionCodesSupplier moduleExceptionSupplier) {
        List<IModuleExceptionCode> moduleExceptionCodes = moduleExceptionSupplier.supply();
        validate(moduleExceptionCodes);
        return moduleExceptionCodes.stream().collect(Collectors.toMap(
            IModuleExceptionCode::getFullModuleId,
            aEnum -> aEnum.findExceptionCode().get()
        ));
    }

    private static void validate(List<IModuleExceptionCode> moduleExceptionCodes) {
        validateExceptionCode(moduleExceptionCodes);
        validateDuplicateModuleId(moduleExceptionCodes);
        validateDuplicateExceptionCodeWithinDifferentFamilies(moduleExceptionCodes);
    }

    private static void validateExceptionCode(List<IModuleExceptionCode> moduleExceptionCodes) {
        List<IModuleExceptionCode> noExceptionCodes = moduleExceptionCodes.stream()
            .filter(moduleExceptionCode -> !moduleExceptionCode.findExceptionCode().isPresent())
            .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(noExceptionCodes)) {
            throw new ExceptionCodeAbsentException(noExceptionCodes);
        }

        Pattern twoUpperAlphabet = Pattern.compile("^[A-Z]{2}$");
        List<IModuleExceptionCode> nonNullAndNonTwoUpperAlphabetExceptionCodes = moduleExceptionCodes.stream()
            .filter(exceptionCode -> !shouldEscape(exceptionCode) && nonNullAndNonTwoUpperAlphabet(twoUpperAlphabet, exceptionCode))
            .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(nonNullAndNonTwoUpperAlphabetExceptionCodes)) {
            throw new ExceptionCodeInvalidException(nonNullAndNonTwoUpperAlphabetExceptionCodes);
        }
    }

    private static boolean shouldEscape(IModuleExceptionCode exceptionCode) {
        return SAMPLES.getModuleId().equals(exceptionCode.getModuleId()) &&
            SAMPLES.getExceptionCode().equals(exceptionCode.getExceptionCode());
    }

    private static boolean nonNullAndNonTwoUpperAlphabet(Pattern twoUpperAlphabet, IModuleExceptionCode exceptionCode) {
        return exceptionCode.getExceptionCode() != null &&
            !ReUtil.isMatch(twoUpperAlphabet, exceptionCode.getExceptionCode());
    }

    private static void validateDuplicateModuleId(List<IModuleExceptionCode> moduleExceptionCodes) {
        Map<String, List<IModuleExceptionCode>> fullModuleIdToExceptionCodes = moduleExceptionCodes.stream()
            .collect(Collectors.groupingBy(IModuleExceptionCode::getFullModuleId));

        List<Map.Entry<String, List<IModuleExceptionCode>>> duplicateFullModuleIdToExceptionCodes = fullModuleIdToExceptionCodes.entrySet()
            .stream()
            .filter(entry -> entry.getValue().size() > 1)
            .collect(Collectors.toList());

        if (CollectionUtil.isNotEmpty(duplicateFullModuleIdToExceptionCodes)) {
            throw new DuplicateModuleIdException(duplicateFullModuleIdToExceptionCodes);
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static void validateDuplicateExceptionCodeWithinDifferentFamilies(
        List<IModuleExceptionCode> moduleExceptionCodes) {
        List<Map.Entry<String, HashSet<IModuleExceptionCode>>>
            duplicateExceptionCodeWithinDifferentFamiliesExceptionCodeToEnumEntries =
            moduleExceptionCodes.stream().collect(
                    Collectors.groupingBy(aEnum -> aEnum.findExceptionCode().get(),
                        new SimpleCollector<IModuleExceptionCode,
                            HashSet<IModuleExceptionCode>,
                            HashSet<IModuleExceptionCode>>(
                            HashSet::new,
                            HashSet::add,
                            (aSet, bSet) -> {
                                aSet.addAll(bSet);
                                return aSet;
                            }
                            , new HashSet<>(Collections.singleton(IDENTITY_FINISH))
                        ))
                )
                .entrySet().stream().filter(entry -> {
                    HashSet<IModuleExceptionCode> sameExceptionCodeEnums = entry.getValue();
                    return doesBelongToDifferentRoot(sameExceptionCodeEnums);
                })
                .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(duplicateExceptionCodeWithinDifferentFamiliesExceptionCodeToEnumEntries)) {
            throw new DuplicateExceptionCodeWithinDifferentFamiliesException(
                duplicateExceptionCodeWithinDifferentFamiliesExceptionCodeToEnumEntries,
                getAvailableExceptionCodes(moduleExceptionCodes));
        }
    }

    private static List<String> getAvailableExceptionCodes(List<IModuleExceptionCode> moduleExceptionCodes) {
        Set<String> nonAvailableExceptionCodes = moduleExceptionCodes.stream()
            .map(IModuleExceptionCode::getExceptionCode)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        String allUpperAlphabet = RandomUtil.BASE_CHAR.toUpperCase();
        List<String> allUpperAlphabets = Arrays.asList(allUpperAlphabet.split(""));
        return allUpperAlphabets.stream()
            .flatMap(upperAlphabet -> allUpperAlphabets.stream().map(upperAlphabet::concat))
            .filter(exceptionCode -> !nonAvailableExceptionCodes.contains(exceptionCode))
            .collect(Collectors.toList());
    }

    private static boolean doesBelongToDifferentRoot(HashSet<IModuleExceptionCode> sameExceptionCodeEnums) {
        return sameExceptionCodeEnums.stream().map(IModuleExceptionCode::getRoot)
            .map(IModuleExceptionCode::getFullModuleId)
            .distinct().count() > 1;
    }
}
