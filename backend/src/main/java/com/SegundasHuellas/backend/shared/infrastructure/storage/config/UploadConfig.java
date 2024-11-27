package com.SegundasHuellas.backend.shared.infrastructure.storage.config;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class UploadConfig {

    Map<String, Object> options;

    public static UploadConfig of(Map<String, Object> options) {
        return UploadConfig.builder()
                           .options(options)
                           .build();
    }
}