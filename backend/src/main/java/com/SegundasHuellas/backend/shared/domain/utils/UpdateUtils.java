package com.SegundasHuellas.backend.shared.domain.utils;

import java.util.Optional;
import java.util.function.Consumer;

public final class UpdateUtils {

    private UpdateUtils() {
    }

    public static <T> void updateIfPresent(T value, Consumer<T> setter) {
        Optional.ofNullable(value)
                .ifPresent(setter);
    }

}
