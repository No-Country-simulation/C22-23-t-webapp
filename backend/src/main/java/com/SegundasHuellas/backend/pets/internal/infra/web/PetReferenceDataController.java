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

@RestController
@RequestMapping("/api/pets/reference-data")
@RequiredArgsConstructor
public class PetReferenceDataController {

    private final BreedService breedService;

    @GetMapping("/breeds")
    public List<BreedResponse> getAllAvailableBreedsForSpecies(@RequestParam("species") Species species) {
        return breedService.getAllBreedsForSpecies(species);
    }

    @GetMapping("/species")
    public List<SpeciesResponse> getAllSpecies() {
        return Arrays.stream(Species.values())
                     .map(SpeciesResponse::new).toList();

    }

    @GetMapping("/genders")
    public List<GenderResponse> getAllGenders() {
        return Arrays.stream(Gender.values())
                     .map(GenderResponse::new).toList();
    }


}
