package com.qsy.os.common.exception.exception;

import cn.hutool.core.collection.CollectionUtil;
import com.qsy.os.common.exception.code.IModuleExceptionCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class DuplicateExceptionCodeWithinDifferentFamiliesException extends RuntimeException {

    private final List<Map.Entry<String, HashSet<IModuleExceptionCode>>>
        duplicateExceptionCodeWithinDifferentFamiliesExceptionCodeToEnumEntries;
    private final List<String> availableExceptionCodes;

    public DuplicateExceptionCodeWithinDifferentFamiliesException(
        List<Map.Entry<String, HashSet<IModuleExceptionCode>>>
            duplicateExceptionCodeWithinDifferentFamiliesExceptionCodeToEnumEntries, List<String> availableExceptionCodes) {
        super(getDuplicateExceptionCodeWithinDifferentFamiliesExceptionMessage(
            duplicateExceptionCodeWithinDifferentFamiliesExceptionCodeToEnumEntries,
            availableExceptionCodes));
        this.duplicateExceptionCodeWithinDifferentFamiliesExceptionCodeToEnumEntries =
            duplicateExceptionCodeWithinDifferentFamiliesExceptionCodeToEnumEntries;
        this.availableExceptionCodes = availableExceptionCodes;
    }

    private static String getDuplicateExceptionCodeWithinDifferentFamiliesExceptionMessage(
        List<Map.Entry<String, HashSet<IModuleExceptionCode>>>
            duplicateExceptionCodeWithinDifferentFamiliesExceptionCodeToEnumEntries, List<String> availableExceptionCodes) {
        return duplicateExceptionCodeWithinDifferentFamiliesExceptionCodeToEnumEntries.stream()
            .map(entry -> String.format("exception code [%s] can not be shared within different families:\n%s\n%s",
                entry.getKey(),
                entry.getValue().stream()
                    .map(aEnum -> String.format("    - [%s]", aEnum.getSimpleQualifiedExceptionCodeName()))
                    .collect(Collectors.joining("\n")),
                Optional.ofNullable(availableExceptionCodes).filter(CollectionUtil::isNotEmpty)
                    .map(nonEmptyAvailableExceptionCodes -> String.join("\n", availableExceptionCodes))
                    .map("you may choose one from following:\n"::concat)
                    .orElse("")
            ))
            .collect(Collectors.joining("\n\n"));
    }
}
