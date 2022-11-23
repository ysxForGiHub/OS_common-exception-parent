package com.qsy.os.common.exception.exception;

import com.qsy.os.common.exception.code.IModuleExceptionCode;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class DuplicateModuleIdException extends RuntimeException {

    private final List<Map.Entry<String, List<IModuleExceptionCode>>> duplicateFullModuleIdToExceptionCodes;

    public DuplicateModuleIdException(List<Map.Entry<String, List<IModuleExceptionCode>>> duplicateFullModuleIdToExceptionCodes) {
        super(getDuplicateFullModuleIdExceptionMessage(duplicateFullModuleIdToExceptionCodes));
        this.duplicateFullModuleIdToExceptionCodes = duplicateFullModuleIdToExceptionCodes;
    }

    private static String getDuplicateFullModuleIdExceptionMessage(
        List<Map.Entry<String, List<IModuleExceptionCode>>> duplicateFullModuleIdToExceptionCodes) {
        return duplicateFullModuleIdToExceptionCodes.stream()
            .map(entry ->
                String.format(
                    "exists duplicate full module id [%s] in following exception code:\n%s",
                    entry.getKey(),
                    entry.getValue().stream()
                        .map(aEnum -> String.format("    - [%s]", aEnum.getSimpleQualifiedExceptionCodeName()))
                        .collect(Collectors.joining("\n"))
                ))
            .collect(Collectors.joining("\n\n"));
    }
}
