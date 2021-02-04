package com.zdl.demo;

import com.zdl.demo.util.properties.CustomGlobalConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@MapperScan("com.zdl.*.mapper")
@SpringBootApplication
@EnableConfigurationProperties({CustomGlobalConfig.class})
public class CodeGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeGeneratorApplication.class, args);
	}

}
