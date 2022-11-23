package com.qsy.os.common.exception.exception.process;

import com.sun.tools.javac.tree.JCTree;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
public class DuplicateEnumCodeProcessException extends ProcessException {
    private List<Map.Entry<String, List<JCTree.JCLiteral>>> duplicateCodeToExpressions;

    @Override
    public CharSequence getMsg() {
        return "duplicate code exists in " + getE().getSimpleName() + " : " +
            duplicateCodeToExpressions.stream().map(Map.Entry::getKey)
                .collect(Collectors.joining(","));
    }
}
