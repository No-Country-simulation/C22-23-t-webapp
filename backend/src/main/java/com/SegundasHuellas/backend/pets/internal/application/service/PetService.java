package com.SegundasHuellas.backend.pets.internal.application.service;

import com.SegundasHuellas.backend.pets.internal.application.dto.CreatePetRequestDto;
import com.SegundasHuellas.backend.pets.internal.application.dto.PetResponseDto;
import com.SegundasHuellas.backend.pets.internal.application.dto.UpdatePetRequestDto;

import java.util.List;

public interface PetService {


    PetResponseDto createPet(CreatePetRequestDto petDto);

    List<PetResponseDto> getAllPets();

    PetResponseDto findById(Long id);

    PetResponseDto updatePet(Long id, UpdatePetRequestDto petDto);

    void deletePet(Long id);
}
