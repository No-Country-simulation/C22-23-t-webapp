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
@Table(name = "breeds")
public class Breed extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "species", nullable = false)
    private Species species;

    @Column(name = "is_species_default", nullable = false)
    private boolean isSpeciesDefault;

    @OneToMany(mappedBy = "breed", fetch = FetchType.LAZY) //TODO: Falta definir el CASCADE adecuado
    private List<Pet> pets = new ArrayList<>();


    // 🔨 Helper para agregar mascotas directamente a la raza.
    public void addPet(Pet pet) {
        if (pets == null) {
            pets = new ArrayList<>();
        }
        pets.add(pet);
    }

}
