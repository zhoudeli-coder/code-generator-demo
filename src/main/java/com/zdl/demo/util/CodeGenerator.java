package com.zdl.demo.util;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.zdl.demo.form.CodeGeneratorForm;
import com.zdl.demo.util.properties.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class CodeGenerator {
    @Autowired
    private CustomGlobalConfig customGlobalConfig;
    @Autowired
    private CustomPackageConfig customPackageConfig;
    @Autowired
    private CustomDataSourceConfig customDataSourceConfig;
    @Autowired
    private CustomTemplateConfig customTemplateConfig;
    @Autowired
    private CustomStrategyConfig customStrategyConfig;

    public void excute(CodeGeneratorForm codeGeneratorForm) {
        AutoGenerator generator = new AutoGenerator();
        String outputDir = codeGeneratorForm.getOutputDir();
        String moduleName = codeGeneratorForm.getModuleName();
        String parent = codeGeneratorForm.getParent();
        Set<String> tableNames = codeGeneratorForm.getTableNames();

        if (Strings.isNotBlank(outputDir)) {
            customGlobalConfig.setOutputDir(outputDir);
        }
        if (Strings.isNotBlank(moduleName)) {
            customPackageConfig.setModuleName(moduleName);
        }
        if (Strings.isNotBlank(parent)) {
            customPackageConfig.setParent(parent);
        }
        if (CollUtil.isNotEmpty(tableNames)) {
            customStrategyConfig.setInclude(tableNames.toArray(new String[0]));
        }

        generator.setGlobalConfig(customGlobalConfig);
        generator.setDataSource(customDataSourceConfig);
        generator.setPackageInfo(customPackageConfig);
        generator.setTemplate(customTemplateConfig);
        generator.setStrategy(customStrategyConfig);

        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 自定义输出配置
        List<FileOutConfig> fileOutConfigList = new ArrayList<>();
        // 自定义配置会被优先输出
        fileOutConfigList.add(new FileOutConfig() {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String outputFilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\mapper\\";
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return outputFilePath + customPackageConfig.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        injectionConfig.setFileOutConfigList(fileOutConfigList);
        generator.setCfg(injectionConfig);

        generator.setTemplateEngine(new FreemarkerTemplateEngine());
        generator.execute();
    }
}
