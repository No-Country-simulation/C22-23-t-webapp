package com.SegundasHuellas.backend.config;

import com.SegundasHuellas.backend.shared.domain.vo.ImageDefaults;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "app.default")
public class DefaultImagesConfig {

    private String petPhoto;


    @PostConstruct
    public void initializeDefaults() {
        if (petPhoto == null) {
            throw new IllegalStateException("Default pet photo URL not configured in application.yml");
        }
        ImageDefaults.initialize(petPhoto);
    }

}
