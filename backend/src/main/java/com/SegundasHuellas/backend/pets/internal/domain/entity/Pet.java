package com.SegundasHuellas.backend.pets.internal.domain.entity;

import com.SegundasHuellas.backend.pets.internal.domain.enums.Gender;
import com.SegundasHuellas.backend.pets.internal.domain.enums.PetStatus;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import com.SegundasHuellas.backend.pets.internal.domain.vo.Age;
import com.SegundasHuellas.backend.pets.internal.domain.vo.VaccinationStatus;
import com.SegundasHuellas.backend.pets.internal.domain.vo.Weight;
import com.SegundasHuellas.backend.shared.domain.base.BaseEntity;
import com.SegundasHuellas.backend.shared.domain.vo.Image;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

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

    @ManyToOne
    @JoinColumn(name = "breed_id")
    private Breed breed;//❓Como validar que la raza agregada pertenezca a la specie correcta.

    @Embedded
    private Age age;//expresado en días.

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PetStatus status;

//    public void setBreed(Breed breed) {
//        if (breed != null && !breed.getSpecies().equals(this.species)) {
//            throw new IllegalArgumentException(
//                    "The breed's species (" + breed.getSpecies() +
//                            ") does not match the pet´s species (" + this.species + ")."
//            );
//        }
//        this.breed = breed;
//    }//Al usar el builder esta validacion no se usa. ❓Hacer customBuilder

    /*
     🔨 Movemos la Species a la entidad Breed. De esta manera nos aseguramos que siempre que se cree una raza,
        se sepa a que especie pertenece y no necesitamos validaciones adicionales.
     🔨 Esto tambien simplifica la manera de settear el breed:
    */
    public void assignBreed(Breed breed) {
        requireNonNull(breed, "Breed cannot be null");

        this.breed = breed;
        breed.addPet(this);
    }

    // Por ejemplo, podríamos usar este factory para crear una pet con solo los valores indispensables.

    // ❗❗❗ Ahora necesitamos asignar el breed a nivel de servicio al registrar una nueva pet. ❗❗❗
    public static Pet withDefaults(String petName, Species species) {
        return Pet.builder()
                  .name(petName)
                  .gender(Gender.UNDEFINED)
//                .image(Image.placeholder()) // Esto todavía no lo implemento. De momento es null.
                  .age(Age.ofDays(0))
                  .vaccinationStatus(VaccinationStatus.notVaccinated()) // Sin vacunas por defecto
                  .weight(Weight.of(0))
                  .isCastrated(false)//asumimos que no esta castrado
                  .healthStatus("Healthy")// Dependiendo de la complejidad, podría ser un value object HealthStatus, y luego llamar a HealthStatus.default() en este campo.
                  .comments("")
                  .birthDate(LocalDate.now()) // Deberia estar sincronizada con Age? Considerar pedir solo un campo, y derivar uno del otro. // check Age.fromDate()
//                  .breed(Breed.defaultBreed(species))// Si esto es una entidad aparte podría ser un Breed.defaultBreed() o Breed.unknownBreed()
                  .status(PetStatus.UNAVAILABLE) //❓ La mascota no esta disponible para adoptar al momento de creacion, hasta que un admin lo permita.
                  .build();
    }


}