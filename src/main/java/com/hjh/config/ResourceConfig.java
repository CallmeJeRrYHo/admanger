package com.hjh.config;

import com.yqh.util.common.PropUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author： Jerry
 * @Descrption：
 * @Date： Create in 10:12 2018/9/12
 */
@Configuration
public class ResourceConfig implements WebMvcConfigurer {
    @Value("${pic.UPLOAD_FILE_PATH_MAPPING}")
    String upload_file_path;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println(upload_file_path);
        registry.addResourceHandler("/pic/**").addResourceLocations(upload_file_path)
        .addResourceLocations("/ad/**").addResourceLocations("classpath:");
    }
}
