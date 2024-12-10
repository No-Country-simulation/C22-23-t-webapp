package com.SegundasHuellas.backend.shared.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "Metadata information for an uploaded image")
public record ImageMetadata(

        @Schema(
                description = "Secure URL to access the image",
                example = "https://res.cloudinary.com/example/image/upload/v1234/sample.jpg"
        )
        String url,

        @Schema(
                description = "Size of the image in bytes",
                example = "1048576"
        )
        long bytes,

        @Schema(
                description = "Human-readable size format",
                example = "1.00 MB"
        )
        String readableSize,

        @Schema(
                description = "Image file format",
                example = "JPG"
        )
        ImageFormat format,

        @Schema(
                description = "Image dimensions"
        )
        Dimensions dimensions
) {
    public static ImageMetadata from(Map<String, Object> cloudinaryResponse) {
        long bytes = Long.parseLong(cloudinaryResponse.get("bytes").toString());
        return new ImageMetadata(
                (String) cloudinaryResponse.get("secure_url"),
                bytes,
                formatSize(bytes),
                ImageFormat.from((String) cloudinaryResponse.get("format")),
                new Dimensions(
                        ((Number) cloudinaryResponse.get("width")).intValue(),
                        ((Number) cloudinaryResponse.get("height")).intValue()
                )
        );
    }

    private static String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.2f KB", bytes / 1024.0);
        return String.format("%.2f MB", bytes / (1024.0 * 1024));
    }

    @Schema(description = "Supported image format types")
    public enum ImageFormat {
        @Schema(description = "JPEG/JPG format") JPG,
        @Schema(description = "PNG format") PNG,
        @Schema(description = "WebP format") WEBP,
        @Schema(description = "Unknown or unsupported format") UNKNOWN;

        public static ImageFormat from(String format) {
            if (format == null) return UNKNOWN;
            return switch (format.toUpperCase()) {
                case "JPG", "JPEG" -> JPG;
                case "PNG" -> PNG;
                case "WEBP" -> WEBP;
                default -> UNKNOWN;
            };
        }
    }

    @Schema(description = "Image width and height dimensions")
    public record Dimensions(
            @Schema(
                    description = "Image width in pixels",
                    example = "1920",
                    minimum = "1"
            )
            int width,

            @Schema(
                    description = "Image height in pixels",
                    example = "1080",
                    minimum = "1"
            )
            int height
    ) {
    }
}