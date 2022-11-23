package com.qsy.os.common.exception.exception.process;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

@Getter
@Setter
@Accessors(chain = true)
public class ProcessException extends RuntimeException{
    private Diagnostic.Kind kind;
    private CharSequence msg;
    private Element e;
}
