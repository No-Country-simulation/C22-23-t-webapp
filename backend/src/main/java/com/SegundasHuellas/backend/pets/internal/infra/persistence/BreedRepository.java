package com.SegundasHuellas.backend.pets.internal.infra.persistence;

import com.SegundasHuellas.backend.pets.internal.application.dto.BreedResponse;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Breed;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BreedRepository extends JpaRepository<Breed, Long> {

//    Optional<Breed> findByNameAndSpecies(String name, Pet.Species species);


    //TODO: Deberia funcionar este method por magia de JPA, pero hay que probarlo
    //Este metodo sirve para obtener la raza por defecto de un especie y asi poder asignarla a una mascota recién creada.
    Optional<Breed> findBySpeciesAndIsSpeciesDefaultTrue(Species species);

    @Query("""
            SELECT new com.SegundasHuellas.backend.pets.internal.application.dto.BreedResponse(
            b.id,
            b.name,
            count(p)
            )
            FROM Breed b
            LEFT JOIN b.pets p
            WHERE b.species = :species
            GROUP BY b.id, b.name
            """)
    List<BreedResponse> findAllBySpeciesAsResponse(Species species);


    List<Breed> findAllBySpecies(Species species);
}
