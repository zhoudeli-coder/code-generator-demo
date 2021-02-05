package com.zdl.demo.config;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * @PropertySource无法加载yaml解决办法
 */
public class YamlAndPropertySourceFactory extends DefaultPropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        if (Objects.isNull(resource)) {
            return super.createPropertySource(name, resource);
        }

        Resource resourceResource = resource.getResource();
        if (Boolean.FALSE.equals(resourceResource.exists())) {
            System.out.println("配置文件不存在" + resource.getResource().getFilename());
            return new PropertiesPropertySource(null, new Properties());
        } else if (resourceResource.getFilename().endsWith(".yml") || resourceResource.getFilename().endsWith(".yaml")) {
            System.out.println("配置文件存在" + resource.getResource().getFilename());
            List<PropertySource<?>> sourceList = new YamlPropertySourceLoader().load(resourceResource.getFilename(), resourceResource);
            if (CollUtil.isNotEmpty(sourceList)) {
                return sourceList.get(0);
            }
        }
        return super.createPropertySource(name, resource);
    }
}
