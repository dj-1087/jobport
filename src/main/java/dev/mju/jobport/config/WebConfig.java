package dev.mju.jobport.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads");
        String uploadPathUri = uploadPath.toUri().toString(); // file:/.../uploads/

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPathUri);
    }
}
