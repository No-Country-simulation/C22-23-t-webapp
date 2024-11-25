package com.SegundasHuellas.backend.pets.internal.infra.persistence;

import com.SegundasHuellas.backend.pets.internal.domain.entity.Breed;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BreedRepository extends JpaRepository<Breed, Long> {

//    Optional<Breed> findByNameAndSpecies(String name, Pet.Species species);


    //TODO: Deberia funcionar este method por magia de JPA, pero hay que probarlo
    //Este metodo sirve para obtener la raza por defecto de un especie y asi poder asignarla a una mascota recién creada.
    Optional<Breed> findBySpeciesAndIsSpeciesDefaultTrue(Species species);
}
