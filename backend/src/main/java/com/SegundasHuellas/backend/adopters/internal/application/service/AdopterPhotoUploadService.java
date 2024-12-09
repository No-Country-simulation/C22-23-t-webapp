package com.SegundasHuellas.backend.adopters.internal.application.service;

import com.SegundasHuellas.backend.adopters.internal.domain.entity.Adopter;
import com.SegundasHuellas.backend.adopters.internal.infra.persistence.AdopterRepository;
import com.SegundasHuellas.backend.shared.application.dto.ImageMetadata;
import com.SegundasHuellas.backend.shared.domain.vo.Image;
import com.SegundasHuellas.backend.shared.domain.vo.ImageDefaults;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import com.SegundasHuellas.backend.shared.infrastructure.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.RESOURCE_NOT_FOUND;
import static com.SegundasHuellas.backend.shared.infrastructure.storage.config.CloudinaryUploadConfig.forUser;

@Service
@Transactional
@RequiredArgsConstructor
public class AdopterPhotoUploadService {

    private final AdopterRepository adopterRepository;
    private final StorageService storageService;

    public ImageMetadata uploadImage(MultipartFile file, Long adopterId) {
        Adopter adopter = adopterRepository.findById(adopterId)
                                           .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND, adopterId.toString()));

        if (!ImageDefaults.isAnyDefaultPhoto(adopter.getProfilePhoto())) {
            storageService.delete(adopter.getProfilePhoto().extractPublicId());
        }

        String uploadId = adopter.getFirstName() + "-" + adopter.getLastName() + "-" + adopter.getId();
        ImageMetadata uploadResults = storageService.upload(file, forUser(adopter.getId(), uploadId));
        adopter.setProfilePhoto(Image.fromUrl(uploadResults.url()));

        return uploadResults;
    }

    public void deleteImage(Long adopterId) {
        Adopter adopter = adopterRepository.findById(adopterId)
                                           .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND, adopterId.toString()));


        if (!ImageDefaults.isAnyDefaultPhoto(adopter.getProfilePhoto())) {
            storageService.delete(adopter.getProfilePhoto().extractPublicId());
            adopter.setProfilePhoto(Image.fromUrl(ImageDefaults.getDefaultUserProfilePhoto()));
        }

    }
}
