package com.zdl.demo.util.properties;

import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.zdl.demo.config.YamlAndPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value = "code-generator.package-config")
@PropertySource(value = "classpath:config/code-generate.yml", factory = YamlAndPropertySourceFactory.class)
public class CustomPackageConfig extends PackageConfig {
}
