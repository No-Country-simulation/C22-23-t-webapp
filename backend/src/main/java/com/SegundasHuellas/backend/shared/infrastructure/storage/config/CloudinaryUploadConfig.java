package com.SegundasHuellas.backend.shared.infrastructure.storage.config;

import lombok.Value;

import java.util.Map;

import static java.util.Objects.requireNonNull;

@Value
public class CloudinaryUploadConfig {

    private static final String resourceType = "auto";

    private static final String displayName = "uploaded-image";

    private static final String folder = "uploaded-images";

    private static final String quality = "auto";

    private static final String fetchFormat = "auto";

    private static final String format = "webp";

    private static final String compression = "low";

    private static final String flags = "lossy";

    public static Map<String, Object> defaultOptions() {
        return Map.of(
                "resource_type", resourceType,
                "display_name", displayName,
                "folder", folder,
                "quality", quality,
                "fetch_format", fetchFormat,
                "format", format,
                "compression", compression,
                "flags", flags
        );
    }


    public static UploadConfig forResource(String folderName, Long resourceId, String resourceName) {
        requireNonNull(folderName, "Folder name cannot be null");
        requireNonNull(resourceId, "Resource ID cannot be null");
        requireNonNull(resourceName, "Resource name cannot be null");

        String identifier = String.format("%s-%s-%s", folderName, resourceId, resourceName);
        return UploadConfig.of(folderName, identifier, defaultOptions());
    }

    public static UploadConfig forPet(Long petId, String petName) {
        return forResource("pet-photos", petId, petName);
    }


}
