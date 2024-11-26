package com.SegundasHuellas.backend.pets.internal.infra.web;

import com.SegundasHuellas.backend.pets.api.dto.CreatePetRequestDto;
import com.SegundasHuellas.backend.pets.api.dto.PetResponseDto;
import com.SegundasHuellas.backend.pets.api.dto.UpdatePetRequestDto;
import com.SegundasHuellas.backend.pets.internal.application.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetService petService;

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

//    @GetMapping("/by-species")
//    public ResponseEntity<List<PetResponseDto>> findPetsBySpecies(@RequestParam("species") String species) {
//        List<PetResponseDto> pets = petService.findBySpecies(species);
//        return ResponseEntity.ok(pets);
//    }

    @GetMapping("/by-breed")
    public ResponseEntity<List<PetResponseDto>> findPetsByBreedName(@RequestParam("breedName") String breedName) {
        List<PetResponseDto> pets = petService.findByBreedName(breedName);
        return ResponseEntity.ok(pets);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponseDto> updatePet(
            @PathVariable Long petId,
            @RequestBody UpdatePetRequestDto petDto
            ) {
        PetResponseDto petResponseDto = petService.updatePet(petId, petDto);
        return ResponseEntity.ok(petResponseDto);
    }

    public ResponseEntity<Void> deletePet(@PathVariable Long petId) {
        petService.deletePet(petId);
        return ResponseEntity.noContent().build();
    }
}
