package com.SegundasHuellas.backend.pets.internal.infra.web;

import com.SegundasHuellas.backend.pets.internal.application.dto.*;
import com.SegundasHuellas.backend.pets.internal.application.service.PetSearchService;
import com.SegundasHuellas.backend.pets.internal.application.service.PetService;
import com.SegundasHuellas.backend.pets.internal.domain.enums.PetStatus;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Size;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import com.SegundasHuellas.backend.pets.internal.domain.vo.Age;
import com.SegundasHuellas.backend.shared.application.dto.PageResponse;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.INVALID_AGE;
import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.RESOURCE_NOT_FOUND;
import static org.springframework.data.domain.Sort.Direction.ASC;

/**
 * REST Controller for managing pet-related operations in the application.
 * Provides endpoints for creating, updating, deleting, retrieving, and searching pets.
 */
@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;
    private final PetSearchService petSearchService;

    /**
     * Creates a new pet based on the provided data.
     *
     * @param petRequestDto the pet to be created
     * @return the created pet
     */
    @PostMapping
    public ResponseEntity<PetResponseDto> createPet(@RequestBody @Valid CreatePetRequestDto petRequestDto) {
        PetResponseDto petResponseDto = petService.createPet(petRequestDto);
        return new ResponseEntity<>(petResponseDto, HttpStatus.CREATED);
    }

    /**
     * Returns a list of all pets.
     *
     * @return a list of pet response data transfer objects
     */
    @GetMapping
    public ResponseEntity<List<PetResponseDto>> getAllPets() {
        // Call the pet service to retrieve the list of pets
        List<PetResponseDto> pets = petService.getAllPets();
        // Return the list of pets in a 200 OK response
        return ResponseEntity.ok(pets);
    }

    /**
     * Retrieves a pet by its ID.
     *
     * @param petId the ID of the pet to be retrieved
     * @return the pet response data transfer object
     */
    @GetMapping("/{petId}")
    public ResponseEntity<PetResponseDto> findPetById(@PathVariable Long petId) {
        PetResponseDto petResponseDto = petService.findById(petId);
        return ResponseEntity.ok(petResponseDto);
    }

    /**
     * Searches for pets based on the provided criteria.
     * <p>
     * This endpoint allows clients to search for pets using various optional filters such as name, species, breed,
     * status, size, and age range. The results are paginated and can be sorted using the pageable parameters.
     * </p>
     *
     * @param name     the name of the pet (optional, partial match)
     * @param species  the species of the pet (optional, exact match)
     * @param breed    the breed of the pet (optional, partial match)
     * @param status   the status of the pet (optional, exact match)
     * @param size     the size of the pet (optional, exact match)
     * @param minAge   the minimum age of the pet (optional, in days)
     * @param maxAge   the maximum age of the pet (optional, in days)
     * @param pageable the pagination and sorting information
     * @return a paginated list of pet search results
     */
    @GetMapping("/search")
    public ResponseEntity<PageResponse<PetSearchResult>> searchPets(
            @RequestParam(required = false, value = "name") String name,
            @RequestParam(required = false, value = "species") Species species,
            @RequestParam(required = false, value = "breed") String breed,
            @RequestParam(required = false, value = "status") PetStatus status,
            @RequestParam(required = false, value = "size") Size size,
            @RequestParam(required = false, value = "minAge") Age minAge,
            @RequestParam(required = false, value = "maxAge") Age maxAge,
            @PageableDefault(sort = "id", direction = ASC) Pageable pageable
    ) {
        try {
            // Ensure minAge is not greater than maxAge
            if (minAge != null && maxAge != null && minAge.getValueInDays() > maxAge.getValueInDays()) {
                // Throw an exception if minAge is greater than maxAge
                throw new DomainException(INVALID_AGE, minAge.toString(), maxAge.toString());
            }
            // Create a search criteria object with the provided parameters
            PetSearchCriteria criteria = new PetSearchCriteria(name, species, breed, status, size, minAge, maxAge);
            // Perform the search using the pet search service and return the results
            return ResponseEntity.ok(petSearchService.searchPets(criteria, pageable));
        } catch (Exception e) {
            // Return a 500 Internal Server Error response in case of an exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Updates a pet with the provided information.
     *
     * @param petId    the ID of the pet to be updated
     * @param petDto   the updated pet data
     * @return the updated pet response data transfer object
     */
    @PutMapping("/{petId}")
    public ResponseEntity<PetResponseDto> updatePet(
            @PathVariable Long petId,
            @RequestBody UpdatePetRequestDto petDto
    ) {
        PetResponseDto petResponseDto = petService.updatePet(petId, petDto);
        return ResponseEntity.ok(petResponseDto);
    }

    /**
     * Deletes a pet by its ID.
     *
     * @param petId the ID of the pet to be deleted
     * @return an empty response with a 204 status code
     */
    @DeleteMapping("/delete/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable Long petId) {
        // Call the deletePet method on the pet service with the provided ID
        petService.deletePet(petId);
        // Return an empty response with a 204 status code
        return ResponseEntity.noContent().build();
    }
}
