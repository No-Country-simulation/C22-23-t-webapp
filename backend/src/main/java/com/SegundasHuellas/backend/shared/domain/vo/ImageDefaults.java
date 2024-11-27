package com.SegundasHuellas.backend.shared.domain.vo;

public class ImageDefaults {


    private static String defaultPetPhoto;

    private ImageDefaults() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void initialize(String petPhoto) {
        defaultPetPhoto = petPhoto;
    }

    public static String getDefaultPetPhoto() {
        return defaultPetPhoto;
    }

}
