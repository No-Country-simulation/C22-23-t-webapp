package com.SegundasHuellas.backend.pets.internal.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents the weight of a pet.
 * <p>
 * The weight is stored in grams and provides utility methods for conversions to kilograms.
 * </p>
 *
 * <p>Instances of this class are immutable and must be created using the {@link #of(Integer)} method.</p>
 *
 */
@Embeddable
@EqualsAndHashCode
@ToString
@Getter
public class Weight {

    private Integer valueInGrams;

    private Weight(Integer valueInGrams) {
        this.valueInGrams = valueInGrams;
    }

    protected Weight() {
    }

    /**
     * Creates a new instance of {@link Weight} with the given value in grams.
     * <p>
     * The weight must be a non-negative value. If the given value is negative,
     * an {@link IllegalArgumentException} will be thrown.
     *
     * @param valueInGrams the weight in grams
     * @return a new instance of {@link Weight}
     */
    public static Weight of(Integer valueInGrams) {
        if (valueInGrams != null && valueInGrams < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        return new Weight(valueInGrams);
    }

    /**
     * Calculates the weight in kilograms based on the stored value in grams.
     * <p>
     * The result is rounded to one decimal place.
     * <p>
     * If the weight is null or zero, the result is 0.0.
     *
     * @return the weight in kilograms
     */
    public double getValueInKilograms() {
        if (valueInGrams == null || valueInGrams == 0) return 0.0;
        return Math.round(valueInGrams / 100.0) / 10.0;
    }
}
