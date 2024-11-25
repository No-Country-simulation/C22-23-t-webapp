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

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "breeds", uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"species", "is_species_default"},
                name = "uk_breed_species_default"
        )
}) // Agregamos este constraint a nivel de BDD para evitar mas de una raza marcada como default.
public class Breed extends BaseEntity {

//    @Autowired
//    private static BreedRepository breedRepository;
    // 🔨 Una entidad no debe tener dependencias. Menos de su propio repositorio.
    // 🔨 Si existe la necesidad de esta dependencia para la logica, mejor moverla a un servicio.

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "species", nullable = false)
    private Species species;

    @Column(name = "is_species_default", nullable = false)
    private boolean isSpeciesDefault; // Usaremos este campo para saber si esta raza es la predeterminada/por defecto.

    //Una raza posee referencias a muchas mascotas.
    @OneToMany(mappedBy = "breed", fetch = FetchType.LAZY) //TODO: Falta definir el CASCADE adecuado
    private List<Pet> pets = new ArrayList<>();


    // 🔨 Helper para agregar mascotas directamente a la raza.
    public void addPet(Pet pet) {
        if (pets == null) {
            pets = new ArrayList<>();
        }
        pets.add(pet);
    }


    // 🔨 Como esta logica depende de obtener un default desde el repo, mejor dejarla en un servicio.
//    public static Breed defaultBreed(Pet.Species species) {
//        if (species == null) {
//            throw new IllegalArgumentException("Species cannot be null");
//        }
//
//        Optional<Breed> unkownBreed = breedRepository.findByNameAndSpecies("Unkown", species);
//        return unkownBreed.orElseGet(() -> {
//            Breed newBreed = Breed.builder()
//                    .name("Unknown")
//                    .species(species)
//                    .build();
//
//            validateBreed(newBreed);
//            return breedRepository.save(newBreed);
//        });
//    }

//    private static void validateBreed(Breed breed) {
//        if (breed.getName() == null || breed.getName().isBlank()) {
//            throw new IllegalArgumentException("Breed name cannot be null or blank");
//        }
//        if (breed.getSpecies() == null) {
//            throw new IllegalArgumentException("Breed species cannot be null");
//        }
//    }


}
