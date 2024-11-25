package com.SegundasHuellas.backend.pets.internal.application.service.impl;

import com.SegundasHuellas.backend.pets.api.dto.CreatePetRequestDto;
import com.SegundasHuellas.backend.pets.api.dto.PetResponseDto;
import com.SegundasHuellas.backend.pets.api.dto.UpdatePetRequestDto;
import com.SegundasHuellas.backend.pets.internal.application.service.PetService;
import com.SegundasHuellas.backend.pets.internal.domain.Breed;
import com.SegundasHuellas.backend.pets.internal.domain.BreedRepository;
import com.SegundasHuellas.backend.pets.internal.domain.Pet;
import com.SegundasHuellas.backend.pets.internal.domain.PetRepository;
import com.SegundasHuellas.backend.pets.internal.domain.vo.Age;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private BreedRepository breedRepository;

    @Override
    public PetResponseDto createPet(CreatePetRequestDto petDto) {
        validatePetRequest(petDto);

        Pet pet = Pet.withDefaults(petDto.name(), Pet.Species.valueOf(petDto.species().toUpperCase()));

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
                .orElseThrow(() -> new IllegalArgumentException("Pet with ID " + id + " not found."));
        return mapToPetResponseDto(pet);
    }

    @Override
    public List<PetResponseDto> findBySpecies(String species) {
        Pet.Species speciesEnum = Pet.Species.valueOf(species.toUpperCase());
        return petRepository.findBySpecies(speciesEnum).stream()
                .map(this::mapToPetResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PetResponseDto> findByBreedName(String breedName) {
        return petRepository.findByBreedName(breedName).stream()
                .map(this::mapToPetResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PetResponseDto updatePet(Long id, UpdatePetRequestDto petDto) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet with ID " + id + " not found"));

        if (petDto.name() != null && !petDto.name().isBlank()) {
            existingPet.setName(petDto.name());
        }

        if (petDto.species() != null) {
            Pet.Species newSpecies = Pet.Species.valueOf(petDto.species().toUpperCase());
            existingPet.setSpecies(newSpecies);
        }

        if (petDto.breedName() != null && !petDto.breedName().isBlank()) {
            Breed newBreed = findBreedByNameAndSpecies(petDto.breedName(), existingPet.getSpecies());
            existingPet.setBreed(newBreed);
        }

        if (petDto.birthDate() != null) {
            existingPet.setBirthDate(petDto.birthDate());
        }

        if (petDto.ageInDays() != null) {
            existingPet.setAge(Age.ofDays(petDto.ageInDays()));
        }

        if (petDto.isCastrated() != null) {
            existingPet.setIsCastrated(petDto.isCastrated());
        }

        if (petDto.gender() != null) {
            existingPet.setGender(Pet.Gender.valueOf(petDto.gender().toUpperCase()));
        }

        if (petDto.healthStatus() != null && !petDto.healthStatus().isBlank()) {
            existingPet.setHealthStatus(petDto.healthStatus());
        }

        if (petDto.comments() != null && !petDto.comments().isBlank()) {
            existingPet.setComments(petDto.comments());
        }

        if (petDto.status() != null) {
            existingPet.setStatus(Pet.PetStatus.valueOf(petDto.status().toUpperCase()));
        }

        Pet updatedPet = petRepository.save(existingPet);
        return mapToPetResponseDto(updatedPet);
    }

    @Override
    public void deletePet(Long id) {
        if (!petRepository.existsById(id)) {
            throw new IllegalArgumentException("Pet with ID " + id + " not found");
        }
        petRepository.deleteById(id);
    }

    private void validatePetRequest(CreatePetRequestDto petDto) {
        if (petDto.name() == null || petDto.name().isBlank()) {
            throw new IllegalArgumentException("Pet name is required");
        }
        if (petDto.species() == null || petDto.species().isBlank()) {
            throw new IllegalArgumentException("Species is required");
        }
    }

    private Breed findBreedByNameAndSpecies(String breedName, Pet.Species species) {
        return breedRepository.findByNameAndSpecies(breedName, species)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Breed not found for name: " + breedName + " and species: " + species));
    }

    private PetResponseDto mapToPetResponseDto(Pet pet) {
        return new PetResponseDto(
                pet.getId(),
                pet.getName(),
                pet.getSpecies().name(),
                pet.getBreed().getName(),
                pet.getIsCastrated(),
                pet.getGender().name(),
                pet.getAge() != null ? pet.getAge().getValueInDays() : null,
                pet.getHealthStatus(),
                pet.getComments(),
                pet.getStatus().name()
        );
    }
}
