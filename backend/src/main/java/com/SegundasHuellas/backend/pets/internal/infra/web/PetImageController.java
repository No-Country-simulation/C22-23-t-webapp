package com.SegundasHuellas.backend.pets.internal.infra.web;

import com.SegundasHuellas.backend.pets.internal.application.service.PetImageUploadService;
import com.SegundasHuellas.backend.shared.application.dto.ImageMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pets/")
@RequiredArgsConstructor
public class PetImageController {

    private final PetImageUploadService petImageUploadService;


    @PostMapping("/{id}/photo")
    @ResponseStatus(HttpStatus.CREATED)
    public ImageMetadata uploadImage(@PathVariable Long id,
                                     @RequestParam("image") MultipartFile image) {

        return petImageUploadService.uploadImage(image, id);
    }

    @DeleteMapping("/{id}/photo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable Long id) {
        petImageUploadService.deleteImage(id);
    }
}
