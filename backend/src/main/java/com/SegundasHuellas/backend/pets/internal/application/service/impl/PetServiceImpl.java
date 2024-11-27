package com.SegundasHuellas.backend.pets.internal.application.service.impl;

import com.SegundasHuellas.backend.pets.api.dto.CreatePetRequestDto;
import com.SegundasHuellas.backend.pets.api.dto.PetResponseDto;
import com.SegundasHuellas.backend.pets.api.dto.UpdatePetRequestDto;
import com.SegundasHuellas.backend.pets.internal.application.exception.PetNotFoundException;
import com.SegundasHuellas.backend.pets.internal.application.service.BreedService;
import com.SegundasHuellas.backend.pets.internal.application.service.PetService;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Breed;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Pet;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Gender;
import com.SegundasHuellas.backend.pets.internal.domain.enums.PetStatus;
import com.SegundasHuellas.backend.pets.internal.domain.vo.Age;
import com.SegundasHuellas.backend.pets.internal.infra.persistence.PetRepository;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.RESOURCE_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    private final BreedService breedService;


    /*
     * Repositorio         -> dto        -> dto
     * PetSearchQuery -> PetSearchRequest -> Page<PetSearchResult>
     * JPQL   -> PetSearchResult -> proyecciones
     *
     * */

    @Override
    public PetResponseDto createPet(CreatePetRequestDto petDto) {
        Breed defaultBreedForSpecies = breedService.getDefaultBreedForSpecies(petDto.species());

        Pet pet = Pet.withDefaults(petDto.name(), petDto.species());
        pet.assignBreed(defaultBreedForSpecies);

        petRepository.save(pet);
        return mapToPetResponseDto(pet);
    }

    @Override
    public List<PetResponseDto> getAllPets() {
        return petRepository.findAll().stream()
                .map(this::mapToPetResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PetResponseDto findById(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND, id.toString()));
        return mapToPetResponseDto(pet);
    }


    @Override
    public PetResponseDto updatePet(Long id, UpdatePetRequestDto petDto) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException(id));

        existingPet.setName(petDto.name() != null && !petDto.name().isBlank() ? petDto.name() : existingPet.getName());
        existingPet.setAge(petDto.ageInDays() != null ? Age.ofDays(petDto.ageInDays()) : existingPet.getAge());
        existingPet.setIsCastrated(petDto.isCastrated() != null ? petDto.isCastrated() : existingPet.getIsCastrated());

        if (petDto.gender() != null) {
            existingPet.setGender(Gender.valueOf(petDto.gender().toUpperCase()));
        }

        existingPet.setHealthStatus(petDto.healthStatus() != null && !petDto.healthStatus().isBlank() ? petDto.healthStatus() : existingPet.getHealthStatus());
        existingPet.setComments(petDto.comments() != null && !petDto.comments().isBlank() ? petDto.comments() : existingPet.getComments());

        if (petDto.status() != null) {
            existingPet.setStatus(PetStatus.valueOf(petDto.status().toUpperCase()));
        }

        Pet updatedPet = petRepository.save(existingPet);
        return mapToPetResponseDto(updatedPet);
    }

    @Override
    public void deletePet(Long id) {
        if (!petRepository.existsById(id)) {
            throw new PetNotFoundException(id);
        }
        petRepository.deleteById(id);
    }

    private PetResponseDto mapToPetResponseDto(Pet pet) {
        return new PetResponseDto(
                pet.getId(),
                pet.getName(),
                pet.getBreed().getSpecies().name(),
                pet.getBreed().getName(),
                pet.getIsCastrated(),
                pet.getGender().name(),
                pet.getAge() != null ? pet.getAge().getValueInDays() : null,// ligados
                pet.getHealthStatus(),
                pet.getComments(),
                pet.getStatus().name()
        );
    }
}
