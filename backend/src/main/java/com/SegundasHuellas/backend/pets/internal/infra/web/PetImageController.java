package com.SegundasHuellas.backend.pets.internal.infra.web;

import com.SegundasHuellas.backend.pets.internal.application.service.PetImageUploadService;
import com.SegundasHuellas.backend.shared.application.dto.ImageMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST Controller for managing pet image upload and deletion.
 * Provides endpoints for uploading and deleting images associated with pets.
 */
@RestController
@RequestMapping("/api/pets/")
@RequiredArgsConstructor
public class PetImageController {

    private final PetImageUploadService petImageUploadService;


    /**
     * Uploads an image for the specified pet.
     * The uploaded image is associated with the pet by its ID.
     *
     * @param id    the ID of the pet for which the image is being uploaded
     * @param image the image file to upload
     * @return an {@link ImageMetadata} object containing information about the uploaded image
     * @throws PetNotFoundException if no pet is found with the specified ID
     * @throws ImageUploadException if the image upload fails
     */
    @PostMapping("/{id}/photo")
    @ResponseStatus(HttpStatus.CREATED)
    public ImageMetadata uploadImage(@PathVariable Long id,
                                     @RequestParam("image") MultipartFile image) {
        // Call the service to upload the image and return the resulting metadata
        return petImageUploadService.uploadImage(image, id);
    }

    /**
     * Deletes the image associated with the specified pet.
     *
     * @param id the ID of the pet whose image is to be deleted
     * @throws PetNotFoundException if no pet is found with the specified ID
     * @throws ImageNotFoundException if no image is found for the specified pet
     */
    @DeleteMapping("/{id}/photo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable Long id) {
        // Call the service to delete the image associated with the pet ID
        petImageUploadService.deleteImage(id);
    }
}
