package com.zdl.demo.form;

import lombok.Data;

import java.util.Set;

@Data
public class CodeGeneratorForm {

    /**
     * 生成文件的输出目录【默认 D 盘根目录】
     */
    private String outputDir = System.getProperty("user.dir") + "\\src\\main\\java";

    /**
     * 父包模块名
     */
    private String parent;

    /**
     * 父包模块名
     */
    private String moduleName;

    /**
     * 需要包含的表名，允许正则表达式（与exclude二选一配置）
     */
    private final Set<String> tableNames;
}
