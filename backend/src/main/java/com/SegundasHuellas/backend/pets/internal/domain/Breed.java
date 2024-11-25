package com.SegundasHuellas.backend.pets.internal.domain;

import com.SegundasHuellas.backend.shared.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "breeds")
public class Breed extends BaseEntity {

    @Autowired
    private static BreedRepository breedRepository;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "species", nullable = false)
    private Pet.Species species;//Relación con la especie.

    public static Breed defaultBreed(Pet.Species species) {
        if (species == null) {
            throw new IllegalArgumentException("Species cannot be null");
        }

        Optional<Breed> unkownBreed = breedRepository.findByNameAndSpecies("Unkown", species);
        return unkownBreed.orElseGet(() -> {
            Breed newBreed = Breed.builder()
                    .name("Unknown")
                    .species(species)
                    .build();

            validateBreed(newBreed);
            return breedRepository.save(newBreed);
        });
    }

    private static void validateBreed(Breed breed) {
        if (breed.getName() == null || breed.getName().isBlank()) {
            throw new IllegalArgumentException("Breed name cannot be null or blank");
        }
        if (breed.getSpecies() == null) {
            throw new IllegalArgumentException("Breed species cannot be null");
        }
    }
}
