package com.SegundasHuellas.backend.pets.internal.domain;

import com.SegundasHuellas.backend.pets.internal.domain.vo.Age;
import com.SegundasHuellas.backend.pets.internal.domain.vo.VaccinationStatus;
import com.SegundasHuellas.backend.pets.internal.domain.vo.Weight;
import com.SegundasHuellas.backend.shared.domain.base.BaseEntity;
import com.SegundasHuellas.backend.shared.domain.vo.Image;
import jakarta.persistence.*;
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
    private String name;

    @Embedded
    private Image photo;

    @Column(name = "species", nullable = false)
    private Species species;//dog, cat, other.

    @Column(name = "breed")
    @Size(max = 50, message = "Breed must be 50 characters or less.")
    private String breed;//Hacer lista de especies para cambiar esta propiedad por un enum.

    @Embedded
    private Age age;//expresado en dias.

    @Embedded
    private VaccinationStatus vaccinationStatus;

    @Embedded
    private Weight weight;//en gramos.

    @Column(name = "is_castrated", nullable = false)
    private Boolean isCastrated;//si esta castrado o no.

    @Column(name = "health_status", length = 500)//ultima linea de defensa contra el client.
    private String healthStatus;//aclarar cualquier cosa a tener en cuenta sobre su salud.

    @Column(name = "comments", length = 1000)
    private String comments;

    @Column(name = "birth_date", nullable = true)
    private LocalDate birthDate;//se asume que el front maneja el valor default si es null.

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = true)
    private Gender gender;


    // Por ejemplo, podríamos usar este factory para crear una pet con solo los valores indispensables.
    public static Pet createDefault(String petName, Species species) {
        return Pet.builder()
                  .name(petName)
                  .species(species)
                  .gender(Gender.UNDEFINED)
//                .image(Image.placeholder()) // Esto todavía no lo implemento.
                  .age(Age.ofDays(0))
                  .vaccinationStatus(VaccinationStatus.notVaccinated()) // Sin vacunas por defecto
                  .weight(Weight.of(0))
                  .isCastrated(false)//asumimos que no esta castrado
                  .healthStatus("Healthy")// Dependiendo de la complejidad, podría ser un value object HealthStatus, y luego llamar a HealthStatus.default() en este campo.
                  .comments("")
                  .birthDate(LocalDate.now()) // Deberia estar sincronizada con Age? Considerar pedir solo un campo, y derivar uno del otro. // check Age.fromDate()
                  .breed("Unknown")// Si esto es una entidad aparte podría ser un Breed.defaultBreed() o Breed.unknownBreed()
//                .status(Status.UNAVAILABLE) //❓ La mascota no esta disponible para adoptar al momento de creacion, hasta que se ingresen datos adicionales??
                  .build();

        /*
        * ❓ Deberíamos guardar en la entidad un valor de "status" (enum) que indique DISPONIBLE, NO_DISPONIBLE, ADOPTADA???
        * */
    }

    public enum Gender {//ver si no se sabe.
        MALE, FEMALE, UNDEFINED
    }


    public enum Species {
        DOG, CAT, OTHER
    } //Desarrollar lista con variedad de especies ?

}
