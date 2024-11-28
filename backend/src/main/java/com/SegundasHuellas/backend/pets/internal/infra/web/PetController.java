package com.SegundasHuellas.backend.pets.internal.infra.web;

import com.SegundasHuellas.backend.pets.api.dto.*;
import com.SegundasHuellas.backend.pets.internal.application.service.PetSearchService;
import com.SegundasHuellas.backend.pets.internal.application.service.PetService;
import com.SegundasHuellas.backend.pets.internal.domain.enums.PetStatus;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import com.SegundasHuellas.backend.shared.application.dto.PageResponse;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<PetResponseDto> findPetById(@PathVariable Long petId) {
        PetResponseDto petResponseDto = petService.findById(petId);
        return ResponseEntity.ok(petResponseDto);
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<PetSearchResult>> searchPets(
            @RequestParam(required = false, value = "name") String name,
            @RequestParam(required = false, value = "species") Species species,
            @RequestParam(required = false, value = "breed") String breed,
            @RequestParam(required = false, value = "status") PetStatus status,
            @PageableDefault(sort = "id", direction = ASC) Pageable pageable
    ) {
        PetSearchCriteria criteria = new PetSearchCriteria(name, species, breed, status);
        return ResponseEntity.ok(petSearchService.searchPets(criteria, pageable));
    }

    @PutMapping("/{petId}")
    public ResponseEntity<PetResponseDto> updatePet(
            @PathVariable Long petId,
            @RequestBody UpdatePetRequestDto petDto
    ) {
        PetResponseDto petResponseDto = petService.updatePet(petId, petDto);
        return ResponseEntity.ok(petResponseDto);
    }

    @DeleteMapping("/delete/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable Long petId) {
        petService.deletePet(petId);
        return ResponseEntity.noContent().build();
    }
}
