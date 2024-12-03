package com.SegundasHuellas.backend.config;

import com.cloudinary.Cloudinary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CloudinaryConfig {

    @Value("${app.cloudinary.url}")
    private String url;

    @Bean
    public Cloudinary cloudinary() {
        if (url == null || url.isBlank()) {
            throw new IllegalStateException("🔴 Cloudinary URL is not configured");
        }
        log.info("🟢 Connected successfully to Cloudinary");
        return new Cloudinary(url);
    }

}
