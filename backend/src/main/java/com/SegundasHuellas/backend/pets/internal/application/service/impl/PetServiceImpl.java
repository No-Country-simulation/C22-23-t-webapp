package com.SegundasHuellas.backend.pets.internal.application.service.impl;

import com.SegundasHuellas.backend.auth.api.SecurityPort;
import com.SegundasHuellas.backend.pets.internal.application.dto.CreatePetRequestDto;
import com.SegundasHuellas.backend.pets.internal.application.dto.PetResponseDto;
import com.SegundasHuellas.backend.pets.internal.application.dto.UpdatePetRequestDto;
import com.SegundasHuellas.backend.pets.internal.application.service.BreedService;
import com.SegundasHuellas.backend.pets.internal.application.service.PetService;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Breed;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Pet;
import com.SegundasHuellas.backend.pets.internal.domain.vo.Age;
import com.SegundasHuellas.backend.pets.internal.infra.persistence.BreedRepository;
import com.SegundasHuellas.backend.pets.internal.infra.persistence.PetRepository;
import com.SegundasHuellas.backend.shared.application.dto.ImageResponse;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.SegundasHuellas.backend.shared.domain.utils.UpdateUtils.updateIfPresent;
import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.RESOURCE_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final SecurityPort securityPort;
    private final BreedService breedService;
    private final BreedRepository breedRepository;

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
        pet.setProviderId(securityPort.getCurrentUserId());
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
    public PetResponseDto updatePet(Long id, UpdatePetRequestDto request) {

        Pet pet = petRepository.findById(id)
                               .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND, id.toString()));

        updatePetFromRequest(pet, request);
        Pet updated = petRepository.save(pet);
        return mapToPetResponseDto(updated);
    }

    @Override
    public void deletePet(Long id) {
        if (!petRepository.existsById(id)) {
            throw new DomainException(RESOURCE_NOT_FOUND, id.toString());
        }
        petRepository.deleteById(id);
    }

    private void updatePetFromRequest(Pet existingPet, UpdatePetRequestDto update) {

        breedRepository.findByName(update.breedName())
                       .ifPresent(existingPet::assignBreed);

        Optional.ofNullable(update.ageInDays())
                .map(Age::ofDays)
                .ifPresent(existingPet::setAge);

        updateIfPresent(update.name(), existingPet::setName);
        updateIfPresent(update.isCastrated(), existingPet::setIsCastrated);
        updateIfPresent(update.status(), existingPet::setStatus);
        updateIfPresent(update.gender(), existingPet::setGender);
        updateIfPresent(update.healthStatus(), existingPet::setHealthStatus);
        updateIfPresent(update.comments(), existingPet::setComments);
    }

    private PetResponseDto mapToPetResponseDto(Pet pet) {
        return new PetResponseDto(
                pet.getId(),
                pet.getName(),
                pet.getBreed().getSpecies().getTranslation(),
                pet.getBreed().getName(),
                pet.getPhotos().stream().map(ImageResponse::from).toList(),
                pet.getIsCastrated(),
                pet.getGender().getTranslation(),
                pet.getAge() != null ? pet.getAge().getValueInDays() : null,// ligados
                pet.getHealthStatus(),
                pet.getComments(),
                pet.getStatus().getTranslation(),
                pet.getSize().getTranslation()
        );
    }
}
