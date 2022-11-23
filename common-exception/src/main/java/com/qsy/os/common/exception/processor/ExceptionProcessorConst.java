package com.qsy.os.common.exception.processor;

import com.qsy.os.common.exception.code.supplier.EnumModuleExceptionCodesSupplier;
import com.qsy.os.common.exception.code.supplier.IModuleExceptionCodesSupplier;

public interface ExceptionProcessorConst {
    IModuleExceptionCodesSupplier PROCESSOR_EXCEPTION_CODES_SUPPLIER = new EnumModuleExceptionCodesSupplier();
    String OPTION_KEY_PROJECT_ROOT_PATH = "_projectRootPath";
    String JC_TREE_PREFIX_ENUM_VARIABLE = "/*public static final*/";
    String JC_VARIABLE_NAME_CODE = "code";
    String FILE_NAME_POM_XML = "/pom.xml";
    String POM_ELEMENT_ARTIFACT_ID = "artifactId";
    String POM_ELEMENT_GROUP_ID = "groupId";
    String POM_ELEMENT_PARENT = "parent";
    String PROCESSOR_OPTION_CONFIG_DIRECTION = "option " + OPTION_KEY_PROJECT_ROOT_PATH + " is required, you need to specify it in build argument following all two steps:\n" +
        "    1. supposed you are using idea, you may define it in :\n" +
        "        Preferences | Build, Execution, Deployment | Compiler | Java Compiler\n" +
        "        config compiler parameters per-module with ```-A" + OPTION_KEY_PROJECT_ROOT_PATH + "=${your project root path}```\n" +
        "    2. supposed you are using maven also you may define it by maven plugin:\n" +
        "        ```\n" +
        "            <plugin>\n" +
        "                <groupId>org.apache.maven.plugins</groupId>\n" +
        "                <artifactId>maven-compiler-plugin</artifactId>\n" +
        "                <configuration>\n" +
        "                    <compilerArgs>\n" +
        "                        <compilerArg>-A" + OPTION_KEY_PROJECT_ROOT_PATH + "=${pom.basedir}</compilerArg>\n" +
        "                    </compilerArgs>\n" +
        "                </configuration>\n" +
        "            </plugin>\n" +
        "        ```";
}
