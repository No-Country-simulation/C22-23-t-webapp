package com.SegundasHuellas.backend.adoptions.infra.web;

import com.SegundasHuellas.backend.adoptions.application.dto.AdoptionRequestsUpdateDto;
import com.SegundasHuellas.backend.adoptions.application.dto.AdoptionsRequestDetailsResponse;
import com.SegundasHuellas.backend.adoptions.application.dto.CreateAdoptionsRequestDto;
import com.SegundasHuellas.backend.adoptions.application.service.AdoptionsRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/adoptions-requests")
@RequiredArgsConstructor
public class AdoptionsRequestController {

    private final AdoptionsRequestService adoptionsRequestService;

    @PostMapping("/create")
    public AdoptionsRequestDetailsResponse createAdoptionsRequest (CreateAdoptionsRequestDto adoptionsRequestDto) {return adoptionsRequestService.createAdoptionsRequest(adoptionsRequestDto);}

    @GetMapping
    public List<AdoptionsRequestDetailsResponse> getAllAdoptionsRequests(){return adoptionsRequestService.getAllAdoptionsRequests();}

    @GetMapping("/{id}")
    public AdoptionsRequestDetailsResponse findById(@PathVariable(name = "id" ) Long id) {return adoptionsRequestService.findById(id);}

    @GetMapping("{id}")
    public AdoptionsRequestDetailsResponse findByAdopterId(@PathVariable(name = "id" ) Long id) {return adoptionsRequestService.findByAdopterId(id);}

    @GetMapping("/{id}")
    public AdoptionsRequestDetailsResponse findByPetId(@PathVariable (name = "id" ) Long id) {return adoptionsRequestService.findByPetId(id);}

    @GetMapping("/{id}")
    public AdoptionsRequestDetailsResponse findByPetProviderID(@PathVariable (name = "id") Long id) {return adoptionsRequestService.findByPetProviderId(id);}

    @PutMapping("/update/{id}")
    public AdoptionsRequestDetailsResponse updateAdoptionsRequests(@PathVariable (name = "id") Long id, @RequestBody AdoptionRequestsUpdateDto updateDto) {
        return adoptionsRequestService.updateAdoptionsRequests(id, updateDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAdoptionRequest(@PathVariable (name = "id") Long id) {adoptionsRequestService.deleteAdoptionsRequest(id);}
}
