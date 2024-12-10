package com.SegundasHuellas.backend.pets.internal.application.service;

import com.SegundasHuellas.backend.pets.internal.domain.entity.Pet;
import com.SegundasHuellas.backend.pets.internal.infra.persistence.PetRepository;
import com.SegundasHuellas.backend.shared.application.dto.ImageMetadata;
import com.SegundasHuellas.backend.shared.domain.vo.Image;
import com.SegundasHuellas.backend.shared.domain.vo.ImageDefaults;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import com.SegundasHuellas.backend.shared.infrastructure.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.INVALID_STATE;
import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.RESOURCE_NOT_FOUND;
import static com.SegundasHuellas.backend.shared.infrastructure.storage.config.CloudinaryUploadConfig.forPet;

@Service
@Transactional
@RequiredArgsConstructor
public class PetImageUploadService {
    private static final int MAX_PHOTOS = 4;


    private final StorageService storageService;
    private final PetRepository petRepository;

    public ImageMetadata uploadImage(MultipartFile file, Long petId) {
        Pet pet = getPetById(petId);

        if (pet.getPhotos().size() >= MAX_PHOTOS) {
            throw new DomainException(INVALID_STATE, "Maximum number of photos (" + MAX_PHOTOS + ") reached");
        }


        ImageMetadata uploadResults = storageService.upload(file, forPet(petId, pet.getName()));
        pet.getPhotos().add(Image.fromUrl(uploadResults.url()));

        return uploadResults;
    }

    public void deleteImage(Long petId) {

        //TODO: Fix for multiple photos
//        Pet pet = getPetById(petId);
//
//        if (!ImageDefaults.isAnyDefaultPhoto(pet.getPhoto())) {
//            storageService.delete(pet.getPhoto().extractPublicId());
//            pet.setPhoto(Image.fromUrl(ImageDefaults.getDefaultPetPhoto()));
//        }

    }


    private Pet getPetById(Long petId) {
        return petRepository.findById(petId)
                            .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND, petId.toString()));
    }


}
