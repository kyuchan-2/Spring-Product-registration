package com.dcu.test.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WevMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
        resourceHandlerRegistry
                .addResourceHandler("/upload/images/**")
                .addResourceLocations("file:./src/main/resources/static/images/"); // 실제 파일 경로로 변경

    }
}
