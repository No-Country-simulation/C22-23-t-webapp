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



    @Column(name = "image_url")
    private String url;


    protected Image() {
    }

    private Image(String url) {
        this.url = Objects.requireNonNull(url, "Original URL cannot be null");
    }

    public static Image fromUrl(String url) {
        return new Image(url);
    }

    public String getThumbnailUrl() {
        return adjustForLowSize(url);
    }

    public static Image withDefaults() {
        return new Image(ImageDefaults.getDefaultPetPhoto());
    }

    public boolean isDefaultPhoto() {
        return url.equals(ImageDefaults.getDefaultPetPhoto());
    }

    public String extractPublicId() {
        return url.substring(url.lastIndexOf('/') + 1)
                  .split("\\.")[0];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image image)) return false;
        return Objects.equals(url, image.url);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(url);
    }

    private static String adjustForLowSize(String secureUrl) {
        if (secureUrl == null) return null;
        return secureUrl.replace("/upload/", "/upload/w_400,c_scale,q_auto,f_auto,dpr_auto/");
    }
}
