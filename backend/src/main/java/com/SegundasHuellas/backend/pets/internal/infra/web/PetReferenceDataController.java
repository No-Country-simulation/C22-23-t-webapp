package com.SegundasHuellas.backend.pets.internal.infra.web;

import com.SegundasHuellas.backend.pets.internal.application.dto.BreedResponse;
import com.SegundasHuellas.backend.pets.internal.application.dto.GenderResponse;
import com.SegundasHuellas.backend.pets.internal.application.dto.SpeciesResponse;
import com.SegundasHuellas.backend.pets.internal.application.service.BreedService;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Gender;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * REST Controller for retrieving reference data related to pets.
 * This includes available species, breeds for a species, and genders.
 */
@RestController
@RequestMapping("/api/pets/reference-data")
@RequiredArgsConstructor
public class PetReferenceDataController {

    private final BreedService breedService;

    /**
     * Retrieves a list of available breeds for a given species.
     * <p>
     * This endpoint provides a list of breeds associated with the given species.
     * </p>
     *
     * @param species the species to search for
     * @return a list of breeds associated with the given species
     */
    @GetMapping("/breeds")
    public List<BreedResponse> getAllAvailableBreedsForSpecies(@RequestParam("species") Species species) {
        return breedService.getAllBreedsForSpecies(species);
    }

    /**
     * Retrieves a list of available pet species.
     * <p>
     * This endpoint provides a list of all available pet species.
     * </p>
     *
     * @return a list of available pet species
     */
    @GetMapping("/species")
    public List<SpeciesResponse> getAllSpecies() {
        return Arrays.stream(Species.values())
                     .map(SpeciesResponse::new).toList();

    }

    /**
     * Retrieves a list of all available pet genders.
     * <p>
     * This endpoint provides a list of all available pet genders, including
     * male, female, and undefined.
     * </p>
     *
     * @return a list of all available pet genders
     */
    @GetMapping("/genders")
    public List<GenderResponse> getAllGenders() {
        return Arrays.stream(Gender.values())
                     .map(GenderResponse::new).toList();
    }
}
