package com.SegundasHuellas.backend.pets.internal.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ContactInfo {

    String fullAddress;
    String phone;
    String email;

    public static ContactInfo withDefaults() {
        return new ContactInfo(
                "Sin definir",
                "Sin definir",
                "Sin definir"
        );
    }


}
