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

@Embeddable
@EqualsAndHashCode
@ToString
@Getter
public class Age {

    //Valores por defecto aproximados, fuente: IA 😅
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

    // Puede que no sea necesario si solo vamos a solicitar dias, o si el valor viene corregido desde el client. Pero se puede dar la funcionalidad.
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
        Objects.requireNonNull(birthDate, "Birth date cannot be null");

        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }

        return Age.ofDays((int) ChronoUnit.DAYS.between(birthDate, LocalDate.now()));
    }

    //Conversores simples, se pueden hacer a nivel de client, o podemos mandar los valores listos en un dto.
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
