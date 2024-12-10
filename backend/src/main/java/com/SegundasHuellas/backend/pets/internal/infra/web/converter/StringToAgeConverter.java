package com.SegundasHuellas.backend.pets.internal.infra.web.converter;

import com.SegundasHuellas.backend.pets.internal.domain.vo.Age;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.INVALID_AGE;

@Component
public class StringToAgeConverter implements Converter<String, Age> {

    @Override
    public Age convert(String source) {
        try {
            return Age.ofDays(Integer.parseInt(source));
        } catch (NumberFormatException e) {
            throw new DomainException(INVALID_AGE, source);
        }
    }
}
