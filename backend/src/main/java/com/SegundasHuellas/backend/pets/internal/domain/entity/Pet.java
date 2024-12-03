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

import static java.util.Objects.requireNonNull;

/**
 * Represents a pet available for adoption.
 * <p>
 * This entity stores details about the pet, including its name, age, weight, health status,
 * vaccination status, and adoption status. It is linked to a {@link com.SegundasHuellas.backend.pets.internal.domain.entity.Breed Breed}
 * which associates the pet with a specific species.
 * </p>
 *
 * <p>
 * <strong>Note:</strong> The {@link #assignBreed(Breed)} method enforces breed assignment, ensuring consistency
 * between the pet and its species.
 * </p>
 *
 */

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pets")
public class Pet extends BaseEntity {

    /**
     * The name of the pet.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The photo of the pet.
     */
    @Embedded
    private Image photo;

    /**
     * The breed of the pet, associated with a specific species.
     */
    @ManyToOne
    @JoinColumn(name = "breed_id")
    private Breed breed;//❓Como validar que la raza agregada pertenezca a la specie correcta.

    /**
     * The age of the pet, expressed in days.
     */
    @Embedded
    private Age age;//expresado en días.

    /**
     * The vaccination status of the pet.
     */
    @Embedded
    private VaccinationStatus vaccinationStatus;

    /**
     * The weight of the pet, expressed in grams.
     */
    @Embedded
    private Weight weight;//en gramos.

    @Column(name = "is_castrated", nullable = false)
    private Boolean isCastrated;//si esta castrado o no.

    /**
     * The health status of the pet, with a maximum length of 500 characters.
     */
    @Column(name = "health_status", length = 500)//ultima linea de defensa contra el client.
    private String healthStatus;//aclarar cualquier cosa a tener en cuenta sobre su salud.

    /**
     * Additional comments about the pet, with a maximum length of 1000 characters.
     */
    @Column(name = "comments", length = 1000)
    private String comments;

    /**
     * The gender of the pet.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = true)
    private Gender gender;

    /**
     * The adoption status of the pet.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PetStatus status;


    /**
     * Assigns a breed to the pet and establishes a bi-directional relationship.
     * <p>
     * The method ensures that the breed is not null and properly links the pet to the breed.
     * </p>
     *
     * @param breed the breed to assign to the pet
     * @throws NullPointerException if the breed is null
     */
    public void assignBreed(Breed breed) {
        requireNonNull(breed, "Breed cannot be null");

        this.breed = breed;
        breed.addPet(this);
    }

    // Por ejemplo, podríamos usar este factory para crear una pet con solo los valores indispensables.

    /**
     * Factory method to create a pet with default values.
     * <p>
     * This method initializes the pet with essential details and default settings:
     * <ul>
     *     <li>Gender: {@link Gender#UNDEFINED}</li>
     *     <li>Photo: null (placeholder for future implementation)</li>
     *     <li>Age: 0 days</li>
     *     <li>Vaccination Status: Not vaccinated</li>
     *     <li>Weight: 0 grams</li>
     *     <li>Is Castrated: false</li>
     *     <li>Health Status: "Healthy"</li>
     *     <li>Status: {@link PetStatus#UNAVAILABLE}</li>
     * </ul>
     *
     * <p><strong>Note:</strong> The breed must be set later at the service level.</p>
     *
     * @param petName the name of the pet
     * @param species the species of the pet (for context)
     * @return a new {@link Pet} instance with default values
     */
    public static Pet withDefaults(String petName, Species species) {
        return Pet.builder()
                  .name(petName)
                  .gender(Gender.UNDEFINED)
                  .photo(Image.withDefaults()) // Esto todavía no lo implemento. De momento es null.
                  .age(Age.ofDays(0))
                  .vaccinationStatus(VaccinationStatus.notVaccinated()) // Sin vacunas por defecto
                  .weight(Weight.of(0))
                  .isCastrated(false)//asumimos que no esta castrado
                  .healthStatus("Healthy")// Dependiendo de la complejidad, podría ser un value object HealthStatus, y luego llamar a HealthStatus.default() en este campo.
                  .comments("")
                  .status(PetStatus.UNAVAILABLE) //❓ La mascota no esta disponible para adoptar al momento de creacion, hasta que un admin lo permita.
                  .build();
    }
}
