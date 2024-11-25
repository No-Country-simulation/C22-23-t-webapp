package com.SegundasHuellas.backend.pets.internal.application.service;

import com.SegundasHuellas.backend.pets.internal.domain.entity.Pet;
import com.SegundasHuellas.backend.shared.application.dto.ImageMetadata;
import com.SegundasHuellas.backend.shared.domain.vo.Image;
import com.SegundasHuellas.backend.shared.infrastructure.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.SegundasHuellas.backend.shared.infrastructure.storage.config.CloudinaryUploadConfig.forPet;

@Service
@Transactional
@RequiredArgsConstructor
public class PetImageUploadService {

    private final StorageService storageService;
//    private final PetRepository petRepository; //TODO

    public ImageMetadata uploadImage(MultipartFile file, Long petId) {
        Pet pet = new Pet(); //TODO: get pet from repo

        ImageMetadata uploadResults = storageService.upload(file, forPet(petId, pet.getName()));
        pet.setPhoto(Image.fromUrl(uploadResults.url()));//cambie setImage por setPhoto

        return uploadResults;
    }

    public void deleteImage(Long petId) {
        //TODO
    }

}
