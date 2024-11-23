package com.SegundasHuellas.backend.shared.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@Embeddable
public class Image {

    @Column(name = "original_url")
    private String originalUrl;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;


    protected Image() {
    }

    private Image(String originalUrl) {
        this.originalUrl = Objects.requireNonNull(originalUrl, "Original URL cannot be null");
        this.thumbnailUrl = adjustForLowSize(originalUrl);
    }

    public static Image fromUrl(String url) {
        return new Image(url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image image)) return false;
        return Objects.equals(originalUrl, image.originalUrl) && Objects.equals(thumbnailUrl, image.thumbnailUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalUrl, thumbnailUrl);
    }

    private static String adjustForLowSize(String secureUrl) {
        if (secureUrl == null) return null;
        return secureUrl.replace("/upload/", "/upload/w_400,c_scale,q_auto,f_auto,dpr_auto/");
    }
}
