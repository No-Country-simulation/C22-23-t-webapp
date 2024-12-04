package com.SegundasHuellas.backend.shared.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Setter
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    private static final String DEFAULT_VALUE = "Sin definir";

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "country")
    private String country;

    public Address withDefaults() {
        return Address.builder()
                      .street(DEFAULT_VALUE)
                      .city(DEFAULT_VALUE)
                      .state(DEFAULT_VALUE)
                      .zip(DEFAULT_VALUE)
                      .country(DEFAULT_VALUE)
                      .build();
    }


}
