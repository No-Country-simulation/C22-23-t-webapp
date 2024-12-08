package com.SegundasHuellas.backend.shared.domain.vo;

import java.util.Set;

public class ImageDefaults {


    private static String defaultPetPhoto;
    private static String defaultUserProfilePhoto;

    private ImageDefaults() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void initialize(String petPhoto, String userProfilePhoto) {
        defaultUserProfilePhoto = userProfilePhoto;
        defaultPetPhoto = petPhoto;
    }


    public static String getDefaultPetPhoto() {
        return defaultPetPhoto;
    }

    public static String getDefaultUserProfilePhoto() {
        return defaultUserProfilePhoto;
    }

    public static boolean isAnyDefaultPhoto(Image image) {
        if (image == null) {
            return false;
        }

        String url = image.getUrl();
        if (url == null) {
            return false;
        }

        return Set.of(defaultUserProfilePhoto, defaultPetPhoto).contains(url);
    }

}
