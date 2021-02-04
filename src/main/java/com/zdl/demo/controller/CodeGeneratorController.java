package com.zdl.demo.controller;


import com.zdl.demo.form.CodeGeneratorForm;
import com.zdl.demo.util.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhoudeli
 * @since 2021-02-03
 */
@RestController
@RequestMapping("/demo/code")
public class CodeGeneratorController {
    @Autowired
    private CodeGenerator codeGenerator;

    @GetMapping("/generate")
    public Object generateCode(CodeGeneratorForm codeGeneratorForm) {
        codeGenerator.excute(codeGeneratorForm);
        return "代码已生成详见" + codeGeneratorForm.getOutputDir();
    }
}
