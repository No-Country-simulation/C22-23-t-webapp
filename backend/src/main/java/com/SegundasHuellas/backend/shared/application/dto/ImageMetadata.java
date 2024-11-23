package com.SegundasHuellas.backend.shared.application.dto;

import java.util.Map;

public record ImageMetadata(
        String url,
        long bytes,
        String readableSize,
        ImageFormat format,
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

    public enum ImageFormat {
        JPG, PNG, WEBP, UNKNOWN;

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

    public record Dimensions(int width, int height) {
    }
}