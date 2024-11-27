package com.SegundasHuellas.backend.pets.internal.infra.web;

import com.SegundasHuellas.backend.pets.internal.application.dto.BreedResponse;
import com.SegundasHuellas.backend.pets.internal.application.service.BreedService;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/breeds")
@RequiredArgsConstructor
public class BreedsController {

    /*
     * Si este controlador o su lógica asociada crece mucho, separar en su propio módulo
     * */
    private final BreedService breedService;

    @GetMapping
    public List<BreedResponse> getAllAvailableBreedsForSpecies(@RequestParam("species") Species species) {
        return breedService.getAllBreedsForSpecies(species);
    }


}
