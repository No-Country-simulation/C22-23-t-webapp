package com.SegundasHuellas.backend.adopters.internal.infra.web;

import com.SegundasHuellas.backend.adopters.internal.application.service.AdopterPhotoUploadService;
import com.SegundasHuellas.backend.shared.application.dto.ImageMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


//TODO: Consider moving all photo uploading concerns into its own module, and out of the adopters/pets module. DRY
@RestController
@RequestMapping("/api/adopters/")
@RequiredArgsConstructor
public class AdopterPhotoController implements AdopterPhotoApi {

    private final AdopterPhotoUploadService uploadService;


    @PostMapping("/{id}/photo")
    @ResponseStatus(HttpStatus.CREATED)
    public ImageMetadata uploadImage(@PathVariable Long id,
                                     @RequestParam("image") MultipartFile image) {

        return uploadService.uploadImage(image, id);
    }

    @DeleteMapping("/{id}/photo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable Long id) {
        uploadService.deleteImage(id);
    }
}
