package com.SegundasHuellas.backend.shared.application.dto;

import com.SegundasHuellas.backend.shared.domain.vo.Image;

public record ImageResponse(
        String url,
        String thumbnailUrl
) {
    public static ImageResponse from(Image image) {
        return new ImageResponse(image.getOriginalUrl(), image.getThumbnailUrl());
    }
}
