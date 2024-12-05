package com.SegundasHuellas.backend.shared.infrastructure.storage;

import com.SegundasHuellas.backend.shared.application.dto.ImageMetadata;
import com.SegundasHuellas.backend.shared.exception.ImageOperationException;
import com.SegundasHuellas.backend.shared.infrastructure.storage.config.UploadConfig;
import com.SegundasHuellas.backend.shared.infrastructure.storage.validation.FileValidator;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CloudinaryStorageService implements StorageService {

    private final Cloudinary cloudinary;
    private final FileValidator fileValidator;


    @Override
    public ImageMetadata upload(MultipartFile image, UploadConfig config) {
        Map<String, Object> options = config.getOptions();

        fileValidator.validate(image);
        File tempFile = null;
        try {
            tempFile = File.createTempFile("temp", image.getOriginalFilename());
            image.transferTo(tempFile);

            @SuppressWarnings("unchecked")
            Map<String, Object> uploadResult = cloudinary.uploader().upload(tempFile, options);

            return ImageMetadata.from(uploadResult);


        } catch (IOException e) {
            throw new ImageOperationException("Failed to upload photo: " + e.getMessage());

        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    @Override
    public void delete(String publicId) {
        Objects.requireNonNull(publicId, "publicId cannot be null");

        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("invalidate", true));
        } catch (IOException e) {
            throw new ImageOperationException("Failed to delete photo: " + e.getMessage());
        }
    }
}
