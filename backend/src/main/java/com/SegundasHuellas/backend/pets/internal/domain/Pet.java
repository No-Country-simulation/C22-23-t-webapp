package com.SegundasHuellas.backend.pets.internal.domain;

import com.SegundasHuellas.backend.shared.domain.base.BaseEntity;
import com.SegundasHuellas.backend.shared.domain.vo.Image;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    @NotBlank(message = "Name is mandatory.")
    private String name;

    @Embedded
    private Image image;

//    @Enumerated(EnumType.STRING)
    @Column(name = "species", nullable = false)
    @NotNull(message = "Species is mandatory.")
    private String species;//dog, cat, other.

    @Column(name = "breed")
    @Size(max = 50, message = "Breed must be 50 characters or less.")
    private String breed;//Hacer lista de especies para cambiar esta propiedad por un enum.

    @Column(name = "age_in_days", nullable = true)
    private Integer age;//expresado en dias.

    @Column(name = "is_vaccinated", nullable = false)
    @NotNull(message = "Vaccination status is mandatory.")
    private Boolean isVaccinated;//se espera que se aclare en comments.

    @Column(name = "vaccines", nullable = true)
    private String vaccines;//aclarar que tipo de vacunas tiene, si el boolean es false devuelve null y no se muestra en front.

    @Column(name = "is_castrated", nullable = false)
    @NotNull(message = "Castrated status is mandatory.")
    private Boolean isCastrated;//si esta castrado o no.

    @Column(name = "health_status", length = 500)
    @Size(max = 500, message = "Health status must be 500 characters or less.")
    private String healthStatus;//aclarar cualquier cosa a tener en cuenta sobre su salud.

    @Column(name = "comments", length = 1000)
    @Size(max = 1000, message = "Comments must be 1000 characters or less.")
    private String comments;

    @Column(name = "photo_url", nullable = true)
    private String photoUrl;//almacenado en base de datos url String,si se utiliza firebase para imagenes se modifica.


    @Column(name = "birth_date", nullable = true)
    private LocalDate birthDate;//se asume que el front maneja el valor default si es null.

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = true)
    private Gender gender;

    @Column(name = "weight_in_grams", nullable = true)
    private Integer weight;//en gramos.

    public enum Gender {//ver si no se sabe.
        MALE, FEMALE
    }

    public enum Species {
        DOG, CAT, OTHER
    } //Desarrollar lista con variedad de especies ?

}
