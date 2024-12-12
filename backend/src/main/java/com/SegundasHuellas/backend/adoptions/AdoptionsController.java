package com.SegundasHuellas.backend.adoptions;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/adoptions")
public class AdoptionsController {

    private final AdoptionService adoptionService;


    @PostMapping("/adopt")
    public void adoptPet(@RequestBody @Valid AdoptionRequest adoptionRequest) {
        adoptionService.processAdoptionRequest(adoptionRequest);

    }

    @GetMapping("/{adoptionId}")
    public void getAdoption() {

    }

}
