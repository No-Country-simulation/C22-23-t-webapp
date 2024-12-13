package com.SegundasHuellas.backend.pets.internal.application.service;

import com.SegundasHuellas.backend.pets.internal.domain.entity.Pet;
import com.SegundasHuellas.backend.pets.internal.infra.persistence.PetRepository;
import com.SegundasHuellas.backend.shared.application.dto.ImageMetadata;
import com.SegundasHuellas.backend.shared.domain.vo.Image;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import com.SegundasHuellas.backend.shared.infrastructure.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.RESOURCE_NOT_FOUND;
import static com.SegundasHuellas.backend.shared.infrastructure.storage.config.CloudinaryUploadConfig.forPet;

/**
 * Service responsible for handling image upload and deletion for pets.
 * It interacts with the storage service to upload and delete images and
 * ensures that pet entities are updated accordingly with the uploaded image.
 * <p>
 * This service includes methods for:
 * <ul>
 *     <li>Uploading an image and associating it with a specific pet.</li>
 *     <li>Deleting the image of a pet if it exists and is not the default image.</li>
 *     <li>Retrieving a pet from the database by its ID.</li>
 * </ul>
 * </p>
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PetImageUploadService {

    private final StorageService storageService;
    private final PetRepository petRepository;

    /**
     * Uploads an image to the storage service and associates it with a pet.
     * If the pet already has a photo (that is not the default photo),
     * the existing image is deleted from the storage service before uploading the new one.
     * <p>
     * The pet's photo attribute is then updated with the new image URL.
     * </p>
     *
     * @param file  the image file to be uploaded.
     * @param petId the ID of the pet to associate the uploaded image with.
     * @return the metadata of the uploaded image, including the URL and other details.
     * @throws DomainException if the pet cannot be found.
     */
    public ImageMetadata uploadImage(MultipartFile file, Long petId) {
        Pet pet = getPetById(petId);

        // If the pet already has a photo that is not the default one, delete the existing photo
        if (!pet.getPhoto().isDefaultPhoto()) {
            storageService.delete(pet.getPhoto().extractPublicId());
        }

        // Upload the image to the storage service and get the upload results
        ImageMetadata uploadResults = storageService.upload(file, forPet(petId, pet.getName()));
        // Update the pet's photo URL with the uploaded image URL
        pet.setPhoto(Image.fromUrl(uploadResults.url()));

        return uploadResults;
    }

    /**
     * Deletes the image associated with the pet identified by the given pet ID.
     * <p>
     * If the pet does not have an image, or if the image is the default one,
     * this method does nothing.
     * </p>
     *
     * @param petId the ID of the pet whose image is to be deleted.
     * @throws DomainException if the pet cannot be found.
     */
    public void deleteImage(Long petId) {
        Pet pet = getPetById(petId);

        // Check if the pet does not have an image or the image is the default one
        if (!pet.getPhoto().isDefaultPhoto()) {
            // Delete the image from the storage service
            storageService.delete(pet.getPhoto().extractPublicId());
            // Set the pet's image to the default one
            pet.setPhoto(Image.withDefaults());
        }

    }

    /**
     * Retrieves a pet by its ID from the repository.
     * <p>
     * If the pet with the specified ID does not exist, a {@link DomainException} is thrown.
     * </p>
     *
     * @param petId the ID of the pet to retrieve.
     * @return the pet entity with the specified ID.
     * @throws DomainException if the pet cannot be found.
     */
    private Pet getPetById(Long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new DomainException(RESOURCE_NOT_FOUND, petId.toString()));
    }
}
