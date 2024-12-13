package com.SegundasHuellas.backend.pets.internal.domain.entity;

import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import com.SegundasHuellas.backend.shared.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a breed associated with a specific species of pet.
 * <p>
 * This entity defines the characteristics of a breed, including its name, species,
 * and whether it is the default breed for its species. A breed can have multiple pets associated with it.
 * </p>
 *
 * <p>
 * <strong>Relationships:</strong>
 * <ul>
 *     <li>{@link Pet}: A breed can be associated with multiple pets.</li>
 * </ul>
 * </p>
 *
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "breeds")
public class Breed extends BaseEntity {

    /**
     * The name of the breed.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The species this breed belongs to.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "species", nullable = false)
    private Species species;

    /**
     * Indicates if this breed is the default for its species.
     * <p>
     * A default breed is used when no specific breed is assigned to a pet of this species.
     * </p>
     */
    @Column(name = "is_species_default", nullable = false)
    private boolean isSpeciesDefault;

    /**
     * The list of pets associated with this breed.
     */
    @OneToMany(mappedBy = "breed", fetch = FetchType.LAZY) //TODO: Falta definir el CASCADE adecuado
    private List<Pet> pets = new ArrayList<>();


    /**
     * Adds a pet to the breed and ensures the pet is included in the association.
     * <p>
     * This is a helper method that simplifies adding a pet to the breed's list of associated pets.
     * </p>
     *
     * @param pet the pet to be added to this breed
     */
    // 🔨 Helper para agregar mascotas directamente a la raza.
    public void addPet(Pet pet) {
        if (pets == null) {
            pets = new ArrayList<>();
        }
        pets.add(pet);
    }

}
