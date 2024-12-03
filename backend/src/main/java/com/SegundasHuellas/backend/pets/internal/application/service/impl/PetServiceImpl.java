package com.SegundasHuellas.backend.pets.internal.application.service.impl;

import com.SegundasHuellas.backend.pets.internal.application.dto.CreatePetRequestDto;
import com.SegundasHuellas.backend.pets.internal.application.dto.PetResponseDto;
import com.SegundasHuellas.backend.pets.internal.application.dto.UpdatePetRequestDto;
import com.SegundasHuellas.backend.pets.internal.application.service.BreedService;
import com.SegundasHuellas.backend.pets.internal.application.service.PetService;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Breed;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Pet;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Gender;
import com.SegundasHuellas.backend.pets.internal.domain.enums.PetStatus;
import com.SegundasHuellas.backend.pets.internal.domain.vo.Age;
import com.SegundasHuellas.backend.pets.internal.infra.persistence.PetRepository;
import com.SegundasHuellas.backend.shared.application.dto.ImageResponse;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.RESOURCE_NOT_FOUND;

/**
 * Service implementation for managing pets in the system.
 * Provides methods for creating, retrieving, updating, and deleting pets.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    private final BreedService breedService;


    /**
     * Creates a new pet with a default breed for the specified species.
     * <p>
     * This method assigns the default breed for the specified species, creates a new Pet entity,
     * and saves it to the repository.
     * </p>
     *
     * @param petDto the DTO containing the details of the pet to be created
     * @return a PetResponseDto containing the details of the created pet
     */
    @Override
    public PetResponseDto createPet(CreatePetRequestDto petDto) {
        // Retrieve the default breed for the specified species
        Breed defaultBreedForSpecies = breedService.getDefaultBreedForSpecies(petDto.species());

        // Create a new pet with default attributes and assign the breed
        Pet pet = Pet.withDefaults(petDto.name(), petDto.species());
        pet.assignBreed(defaultBreedForSpecies);

        // Save the pet to the repository
        petRepository.save(pet);

        // Map the saved pet to a response DTO and return it
        // This map is needed because the Pet entity has more fields than the PetResponseDto
        // and we don't want to return the extra fields in the response
        return mapToPetResponseDto(pet);
    }

    /**
     * Retrieves all the pets from the repository and maps them to response DTOs.
     *
     * @return a list of response DTOs containing the details of all the pets
     */
    @Override
    public List<PetResponseDto> getAllPets() {
        // Retrieve all the pets from the repository
        List<Pet> pets = petRepository.findAll();

        // Map each pet to a response DTO and collect them in a list
        return pets.stream()
                .map(this::mapToPetResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a pet by its ID from the repository and maps it to a response DTO.
     *
     * @param id the ID of the pet to be retrieved
     * @return the response DTO of the retrieved pet
     * @throws DomainException if the pet doesn't exist
     */
    @Override
    public PetResponseDto findById(Long id) {
        // Retrieve the pet from the repository
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND, id.toString()));

        // Map the retrieved pet to a response DTO and return it
        return mapToPetResponseDto(pet);
    }


    /**
     * Updates a pet with the given ID.
     *
     * @param id    the ID of the pet to be updated
     * @param petDto the request DTO containing the new details of the pet
     * @return the response DTO of the updated pet
     * @throws DomainException if the pet doesn't exist
     */
    @Override
    public PetResponseDto updatePet(Long id, UpdatePetRequestDto petDto) {
        // Retrieve the pet to be updated from the repository
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND, id.toString()));

        // Update the pet with the new details
        existingPet.setName(petDto.name() != null && !petDto.name().isBlank() ? petDto.name() : existingPet.getName());
        existingPet.setAge(petDto.ageInDays() != null ? Age.ofDays(petDto.ageInDays()) : existingPet.getAge());
        existingPet.setIsCastrated(petDto.isCastrated() != null ? petDto.isCastrated() : existingPet.getIsCastrated());

        // Update the gender if it's not null
        if (petDto.gender() != null) {
            existingPet.setGender(Gender.valueOf(petDto.gender().toUpperCase()));
        }

        // Update the health status if it's not null or blank
        existingPet.setHealthStatus(petDto.healthStatus() != null && !petDto.healthStatus().isBlank() ? petDto.healthStatus() : existingPet.getHealthStatus());
        // Update the comments if it's not null or blank
        existingPet.setComments(petDto.comments() != null && !petDto.comments().isBlank() ? petDto.comments() : existingPet.getComments());

        // Update the status if it's not null
        if (petDto.status() != null) {
            existingPet.setStatus(PetStatus.valueOf(petDto.status().toUpperCase()));
        }

        // Save the updated pet to the repository and return the response DTO
        Pet updatedPet = petRepository.save(existingPet);
        return mapToPetResponseDto(updatedPet);
    }

    /**
     * Deletes a pet by its ID.
     *
     * @param id the ID of the pet to be deleted
     * @throws DomainException if the pet doesn't exist
     */
    @Override
    public void deletePet(Long id) {
        // Check if the pet exists in the repository
        if (!petRepository.existsById(id)) {
            // Throw an exception if the pet is not found
            throw new DomainException(RESOURCE_NOT_FOUND, id.toString());
        }
        // Delete the pet from the repository
        petRepository.deleteById(id);
    }

    /**
     * Maps a Pet entity to a PetResponseDto.
     *
     * @param pet the Pet entity to be mapped
     * @return the mapped PetResponseDto
     */
    private PetResponseDto mapToPetResponseDto(Pet pet) {
        // Map each field from the Pet entity to the corresponding field in the PetResponseDto
        return new PetResponseDto(
                pet.getId(), // Map ID
                pet.getName(), // Map name
                pet.getBreed().getSpecies().getTranslation(), // Map species translation
                pet.getBreed().getName(), // Map breed name
                ImageResponse.from(pet.getPhoto()), // Map photo to ImageResponse
                pet.getIsCastrated(), // Map castration status
                pet.getGender().getTranslation(), // Map gender translation
                pet.getAge() != null ? pet.getAge().getValueInDays() : null, // Map age in days if available
                pet.getHealthStatus(), // Map health status
                pet.getComments(), // Map comments
                pet.getStatus().getTranslation() // Map status translation
        );
    }
}
