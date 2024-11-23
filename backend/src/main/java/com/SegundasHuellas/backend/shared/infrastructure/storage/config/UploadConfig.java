package com.SegundasHuellas.backend.shared.infrastructure.storage.config;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class UploadConfig {
    String identifier;
    String folder;
    Map<String, Object> options;

    public static UploadConfig of(String folder, String identifier) {
        return UploadConfig.builder()
                           .folder(folder)
                           .identifier(identifier)
                           .options(Map.of())
                           .build();
    }

    public static UploadConfig of(String folder, String identifier, Map<String, Object> options) {
        return UploadConfig.builder()
                           .folder(folder)
                           .identifier(identifier)
                           .options(options)
                           .build();
    }
}