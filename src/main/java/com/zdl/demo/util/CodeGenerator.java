package com.zdl.demo.util;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
//        "entity_path" -> "E:\workspace\demo\code-generator-demo\src\main\java\com\zdl\demo2\entity"
//        "controller_path" -> "E:\workspace\demo\code-generator-demo\src\main\java\com\zdl\demo2\controller"
//        "xml_path" -> "E:\workspace\demo\code-generator-demo\src\main\java\com\zdl\demo2\mapper\xml"
//        "mapper_path" -> "E:\workspace\demo\code-generator-demo\src\main\java\com\zdl\demo2\mapper"
//        "service_impl_path" -> "E:\workspace\demo\code-generator-demo\src\main\java\com\zdl\demo2\service\impl"
//        "service_path" -> "E:\workspace\demo\code-generator-demo\src\main\java\com\zdl\demo2\service"
        Map<String, String>  pathInfo = CollectionUtils.newHashMapWithExpectedSize(6);
        String userDir = System.getProperty("user.dir");
        String configParent = customPackageConfig.getParent();
        String configEntity = customPackageConfig.getEntity();
        pathInfo.put(ConstVal.ENTITY_PATH, joinPath(userDir + "\\src\\main\\java\\", configParent, Strings.EMPTY, configEntity));
        pathInfo.put(ConstVal.MAPPER_PATH, joinPath(userDir + "\\src\\main\\java\\", configParent, Strings.EMPTY, customPackageConfig.getMapper()));
        pathInfo.put(ConstVal.SERVICE_PATH, joinPath(userDir + "\\src\\main\\java\\", configParent, Strings.EMPTY, customPackageConfig.getService()));
        pathInfo.put(ConstVal.SERVICE_IMPL_PATH, joinPath(userDir + "\\src\\main\\java\\", configParent, Strings.EMPTY, customPackageConfig.getServiceImpl()));
        pathInfo.put(ConstVal.CONTROLLER_PATH, joinPath(userDir + "\\src\\main\\java\\", configParent, Strings.EMPTY, customPackageConfig.getController()));
        pathInfo.put(ConstVal.XML_PATH, joinPath(userDir + "\\src\\main\\resources\\mapper\\", Strings.EMPTY, customPackageConfig.getModuleName(), Strings.EMPTY));
        customPackageConfig.setPathInfo(pathInfo);

        generator.setGlobalConfig(customGlobalConfig);
        generator.setDataSource(customDataSourceConfig);
        generator.setPackageInfo(customPackageConfig);
        generator.setTemplate(customTemplateConfig);
        generator.setStrategy(customStrategyConfig);

        generator.setTemplateEngine(new FreemarkerTemplateEngine());
        generator.execute();
    }

    private String joinPath(String parentDir, String parent, String moduleName, String packageName) {
        if (StringUtils.isBlank(parentDir)) {
            parentDir = System.getProperty("user.dir");
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
        parent = parent.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
        moduleName = moduleName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
        return parentDir + parent + moduleName + File.separator + packageName;
    }
}
