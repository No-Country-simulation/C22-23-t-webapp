package com.SegundasHuellas.backend.pets.internal.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Embeddable
@EqualsAndHashCode
@ToString
@Getter
public class Age {

    //Valores por defecto aproximados, tomando en cuenta el promedio de días por mes / año bisiesto.
    private static final double DAYS_IN_YEAR = 365.25;
    private static final double DAYS_IN_MONTH = 30.44;

    private Integer valueInDays;


    private Age(Integer valueInDays) {
        this.valueInDays = valueInDays;
    }

    protected Age() {
    }

    public static Age ofDays(Integer valueInDays) {
        if (valueInDays != null && valueInDays < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        return new Age(valueInDays);
    }

    public static Age ofYears(Integer valueInYears) {
        if (valueInYears != null && valueInYears < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        if (valueInYears == null) return Age.ofDays(0);

        return Age.ofDays((int) Math.round(valueInYears * DAYS_IN_YEAR));
    }

    public static Age ofMonths(Integer valueInMonths) {
        if (valueInMonths != null && valueInMonths < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        if (valueInMonths == null) return Age.ofDays(0);
        return Age.ofDays((int) Math.round(valueInMonths * DAYS_IN_MONTH));
    }

    //Por si necesitamos obtener la edad en días desde una fecha de nacimiento.
    public static Age fromDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date cannot be null");
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }

        return Age.ofDays((int) ChronoUnit.DAYS.between(birthDate, LocalDate.now()));
    }

    public double getValueInYears() {
        if (valueInDays == null || valueInDays == 0) return 0.0;
        return BigDecimal.valueOf(valueInDays / DAYS_IN_YEAR)
                         .setScale(1, RoundingMode.HALF_UP)
                         .doubleValue();
    }

    public int getValueInMonths() {
        if (valueInDays == null || valueInDays == 0) return 0;
        return BigDecimal.valueOf(valueInDays / DAYS_IN_MONTH)
                         .setScale(1, RoundingMode.HALF_UP)
                         .intValue();
    }


}
