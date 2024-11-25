package com.SegundasHuellas.backend.pets.internal.application.exception;

import com.SegundasHuellas.backend.pets.internal.domain.Pet;

public class BreedNotFoundException extends RuntimeException {
    public BreedNotFoundException(String breedName, Pet.Species species) {
        super("Breed not found by name: " + breedName + " and specie: " + species);
    }
}
