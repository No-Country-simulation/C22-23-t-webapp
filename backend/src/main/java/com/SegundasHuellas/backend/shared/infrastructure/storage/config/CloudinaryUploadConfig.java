package com.SegundasHuellas.backend.shared.infrastructure.storage.config;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

import static java.util.Objects.requireNonNull;

@Value
@Builder
public class CloudinaryUploadConfig {
    @Builder.Default
    private static final String resourceType = "auto";

    @Builder.Default
    private static final String displayName = "uploaded-image";

    @Builder.Default
    private static final String assetFolder = "uploaded-images";

    @Builder.Default
    private static final String quality = "auto";

    @Builder.Default
    private static final String fetchFormat = "auto";

    @Builder.Default
    private static final String format = "webp";

    @Builder.Default
    private static final String compression = "low";

    @Builder.Default
    private static final String flags = "lossy";

    public static Map<String, Object> defaultOptions() {
        return Map.of(
                "resource_type", resourceType,
                "display_name", displayName,
                "asset_folder", assetFolder,
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
