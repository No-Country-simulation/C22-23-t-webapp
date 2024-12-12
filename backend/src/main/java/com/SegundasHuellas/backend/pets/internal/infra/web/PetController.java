package com.SegundasHuellas.backend.pets.internal.infra.web;

import com.SegundasHuellas.backend.pets.internal.application.security.OwnerAccess;
import com.SegundasHuellas.backend.pets.internal.application.dto.*;
import com.SegundasHuellas.backend.pets.internal.application.service.PetSearchService;
import com.SegundasHuellas.backend.pets.internal.application.service.PetService;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Gender;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.INVALID_AGE;
import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;
    private final PetSearchService petSearchService;

    @PostMapping
    public ResponseEntity<PetResponseDto> createPet(@RequestBody @Valid CreatePetRequestDto petRequestDto) {

        PetResponseDto petResponseDto = petService.createPet(petRequestDto);
        return new ResponseEntity<>(petResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PetResponseDto>> getAllPets() {
        List<PetResponseDto> pets = petService.getAllPets();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetResponseDto> findPetById(@PathVariable(name = "petId") Long petId) {
        PetResponseDto petResponseDto = petService.findById(petId);
        return ResponseEntity.ok(petResponseDto);
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<PetSearchResult>> searchPets(
            @RequestParam(required = false, value = "name") String name,
            @RequestParam(required = false, value = "species") Species species,
            @RequestParam(required = false, value = "breed") String breed,
            @RequestParam(required = false, value = "status") PetStatus status,
            @RequestParam(required = false, value = "gender") Gender gender,
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
            PetSearchCriteria criteria = new PetSearchCriteria(name, species, breed, status, gender, size, minAge, maxAge);
            // Perform the search using the pet search service and return the results
            return ResponseEntity.ok(petSearchService.searchPets(criteria, pageable));
        } catch (Exception e) {
            // Return a 500 Internal Server Error response in case of an exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{petId}")
    @OwnerAccess
    public ResponseEntity<PetResponseDto> updatePet(
            @PathVariable Long petId,
            @RequestBody UpdatePetRequestDto petDto
    ) {
        PetResponseDto petResponseDto = petService.updatePet(petId, petDto);
        return ResponseEntity.ok(petResponseDto);
    }

    @DeleteMapping("/delete/{petId}")
    @OwnerAccess
    public ResponseEntity<Void> deletePet(@PathVariable Long petId) {

        petService.deletePet(petId);
        return ResponseEntity.noContent().build();
    }
}
