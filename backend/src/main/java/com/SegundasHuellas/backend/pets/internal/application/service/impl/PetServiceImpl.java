package com.SegundasHuellas.backend.pets.internal.application.service.impl;

import com.SegundasHuellas.backend.pets.api.dto.CreatePetRequestDto;
import com.SegundasHuellas.backend.pets.api.dto.PetResponseDto;
import com.SegundasHuellas.backend.pets.api.dto.UpdatePetRequestDto;
import com.SegundasHuellas.backend.pets.internal.application.exception.InvalidPetDataException;
import com.SegundasHuellas.backend.pets.internal.application.exception.PetNotFoundException;
import com.SegundasHuellas.backend.pets.internal.application.service.BreedService;
import com.SegundasHuellas.backend.pets.internal.application.service.PetService;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Breed;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Pet;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Gender;
import com.SegundasHuellas.backend.pets.internal.domain.enums.PetStatus;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import com.SegundasHuellas.backend.pets.internal.domain.vo.Age;
import com.SegundasHuellas.backend.pets.internal.infra.persistence.BreedRepository;
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
//        validatePetRequest(petDto);
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

//    @Override
//    public List<PetResponseDto> findBySpecies(String species) {
//        Species speciesEnum = Species.valueOf(species.toUpperCase());
//        return petRepository.findBySpecies(speciesEnum).stream()
//                .map(this::mapToPetResponseDto)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<PetResponseDto> findByBreedName(String breedName) {
        return petRepository.findByBreedName(breedName).stream()
                .map(this::mapToPetResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PetResponseDto updatePet(Long id, UpdatePetRequestDto petDto) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException(id));

        existingPet.setName(petDto.name() != null && !petDto.name().isBlank() ? petDto.name() : existingPet.getName());

//        if (petDto.species() != null) {
//            Species newSpecies = Species.valueOf(petDto.species().toUpperCase());
//            existingPet.setSpecies(newSpecies);
//        }
//
//        if (petDto.breedName() != null && !petDto.breedName().isBlank()) {
//            Breed newBreed = findBreedByNameAndSpecies(petDto.breedName(), existingPet.getSpecies());
//            existingPet.setBreed(newBreed);
//        }

        existingPet.setBirthDate(petDto.birthDate() != null ? petDto.birthDate() : existingPet.getBirthDate());
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

//    private void validatePetRequest(CreatePetRequestDto petDto) {
//        if (petDto.name() == null || petDto.name().isBlank()) {
//            throw new InvalidPetDataException("Pet name is required");
//        }
//        if (petDto.species() == null || petDto.species().isBlank()) {
//            throw new InvalidPetDataException("Species is required");
//        }
//    }

//    private Breed findBreedByNameAndSpecies(String breedName, Species species) {
//        return breedRepository.findByNameAndSpecies(breedName, species)
//                .orElseThrow(() -> new BreedNotFoundException(breedName, species));
//    }

    private PetResponseDto mapToPetResponseDto(Pet pet) {
        return new PetResponseDto(
                pet.getId(),
                pet.getName(),
                pet.getBreed().getSpecies().name(),
                pet.getBreed().getName(),
                pet.getBirthDate(),//🔴 ligados -> considerar sacarlo
                pet.getIsCastrated(),
                pet.getGender().name(),
                pet.getAge() != null ? pet.getAge().getValueInDays() : null,// ligados
                pet.getHealthStatus(),
                pet.getComments(),
                pet.getStatus().name()
        );
    }
}
