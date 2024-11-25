package com.SegundasHuellas.backend.pets.internal.application.service;

import com.SegundasHuellas.backend.pets.api.dto.CreatePetRequestDto;
import com.SegundasHuellas.backend.pets.api.dto.PetResponseDto;
import com.SegundasHuellas.backend.pets.api.dto.UpdatePetRequestDto;

import java.util.List;

public interface PetService {


    PetResponseDto createPet(CreatePetRequestDto petDto);

    List<PetResponseDto> getAllPets();

    PetResponseDto findById(Long id);

    List<PetResponseDto> findBySpecies(String species);

    List<PetResponseDto> findByBreedName(String breedName);

    PetResponseDto updatePet(Long id, UpdatePetRequestDto petDto);

    void deletePet(Long id);
}
