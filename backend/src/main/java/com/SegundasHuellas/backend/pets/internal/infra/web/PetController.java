package com.SegundasHuellas.backend.pets.internal.infra.web;

import com.SegundasHuellas.backend.pets.api.dto.*;
import com.SegundasHuellas.backend.pets.internal.application.service.PetSearchService;
import com.SegundasHuellas.backend.pets.internal.application.service.PetService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Page<PetSearchResult>> searchPets(
            @ModelAttribute PetSearchCriteria criteria,
            Pageable pageable
    ) {
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
