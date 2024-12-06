package com.SegundasHuellas.backend.pets.internal.infra.web;

import com.SegundasHuellas.backend.pets.internal.domain.vo.Age;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.INVALID_AGE;

 @Component
public class StringToAgeConverter implements Converter<String, Age> {

    /**
     * Converts a string to an Age object.
     *
     * @param source a string that represents the age of a pet in days
     * @return an Age object
     * @throws DomainException if the string cannot be parsed to an int or the value is negative
     */
    @Override
    public Age convert(String source) {
        // Try to parse the string to an int and return an Age object
        try {
            return Age.ofDays(Integer.parseInt(source));
        } catch (NumberFormatException e) {
            // If the string cannot be parsed, throw a DomainException with a meaningful error code
            throw new DomainException(INVALID_AGE, "The age must be a number greater than 0");
        } catch (Exception e) {
            // If the value is negative, throw a DomainException with a meaningful error code
            throw new DomainException(INVALID_AGE, e.getMessage());
        }
    }
}
