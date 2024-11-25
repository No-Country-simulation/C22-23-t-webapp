package com.SegundasHuellas.backend.pets.internal.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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

    public static Weight of(Integer valueInGrams) {
        if (valueInGrams != null && valueInGrams < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        return new Weight(valueInGrams);
    }

    public double getValueInKilograms() {
        if (valueInGrams == null || valueInGrams == 0) return 0.0;
        return Math.round(valueInGrams / 100.0) / 10.0;
    }
}
