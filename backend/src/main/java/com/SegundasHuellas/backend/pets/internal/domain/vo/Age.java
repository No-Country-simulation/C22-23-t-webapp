package com.SegundasHuellas.backend.pets.internal.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Represents the age of a pet in days, with conversion utilities for months and years.
 * <p>
 * This value object provides a flexible representation of age, allowing
 * calculations and transformations to other units (months or years).
 * </p>
 *
 */
@Embeddable
@EqualsAndHashCode
@ToString
@Getter
public class Age {

    //Valores por defecto aproximados, fuente: IA 😅
    private static final double DAYS_IN_YEAR = 365.25;
    private static final double DAYS_IN_MONTH = 30.44;

    /**
     * Age in days.
     */
    private Integer valueInDays;

    /**
     * Private constructor for controlled creation.
     *
     * @param valueInDays Number of days representing the age.
     */
    private Age(Integer valueInDays) {
        this.valueInDays = valueInDays;
    }

    /**
     * Protected constructor for JPA.
     */
    protected Age() {
    }

    /**
     * Creates an Age object with a given number of days.
     *
     * @param valueInDays Number of days.
     * @return a new instance of {@code Age}.
     * @throws IllegalArgumentException if the number of days is negative.
     */
    public static Age ofDays(Integer valueInDays) {
        if (valueInDays != null && valueInDays < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        return new Age(valueInDays);
    }

    /**
     * Creates an Age object with a given number of years.
     *
     * @param valueInYears Number of years.
     * @return a new instance of {@code Age}.
     */
    // Puede que no sea necesario si solo vamos a solicitar dias, o si el valor viene corregido desde el client. Pero se puede dar la funcionalidad.
    public static Age ofYears(Integer valueInYears) {
        if (valueInYears != null && valueInYears < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        if (valueInYears == null) return Age.ofDays(0);

        return Age.ofDays((int) Math.round(valueInYears * DAYS_IN_YEAR));
    }

    /**
     * Creates an Age object with a given number of months.
     *
     * @param valueInMonths Number of months.
     * @return a new instance of {@code Age}.
     */
    public static Age ofMonths(Integer valueInMonths) {
        if (valueInMonths != null && valueInMonths < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        if (valueInMonths == null) return Age.ofDays(0);
        return Age.ofDays((int) Math.round(valueInMonths * DAYS_IN_MONTH));
    }

    /**
     * Calculates the age in days from a given birth date.
     *
     * @param birthDate Birth date of the pet.
     * @return a new instance of {@code Age}.
     * @throws IllegalArgumentException if the birth date is in the future.
     */
    public static Age fromDate(LocalDate birthDate) {
        Objects.requireNonNull(birthDate, "Birth date cannot be null");

        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }

        return Age.ofDays((int) ChronoUnit.DAYS.between(birthDate, LocalDate.now()));
    }

    /**
     * Converts the age to years.
     *
     * @return Age in years, rounded to one decimal place.
     */
    public double getValueInYears() {
        if (valueInDays == null || valueInDays == 0) return 0.0;
        return BigDecimal.valueOf(valueInDays / DAYS_IN_YEAR)
                         .setScale(1, RoundingMode.HALF_UP)
                         .doubleValue();
    }

    /**
     * Converts the age to months.
     *
     * @return Age in months, rounded to the nearest integer.
     */
    public int getValueInMonths() {
        if (valueInDays == null || valueInDays == 0) return 0;
        return BigDecimal.valueOf(valueInDays / DAYS_IN_MONTH)
                         .setScale(1, RoundingMode.HALF_UP)
                         .intValue();
    }
}
