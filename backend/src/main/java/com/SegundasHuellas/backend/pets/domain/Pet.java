package com.SegundasHuellas.backend.pets.domain;

import com.SegundasHuellas.backend.shared.audit.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pets")
public class Pet extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    //Breed-Entity -> Species-Enum

    private Integer age;

    private Boolean isVaccinated;

    private Boolean isSterilized;

    private String healthStatus;

    //private Image photo;

    private LocalDate birthDate;

    private Gender gender;

    private Double weight;

    public enum Gender {
        MALE, FEMALE
    }


}
