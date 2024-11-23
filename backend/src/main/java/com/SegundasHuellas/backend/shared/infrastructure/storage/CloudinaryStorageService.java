package com.SegundasHuellas.backend.shared.infrastructure.storage;

import com.SegundasHuellas.backend.shared.application.dto.ImageMetadata;
import com.SegundasHuellas.backend.shared.exception.PhotoUploadException;
import com.SegundasHuellas.backend.shared.infrastructure.storage.config.UploadConfig;
import com.SegundasHuellas.backend.shared.infrastructure.storage.validation.FileValidator;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryStorageService implements StorageService{

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
            throw new PhotoUploadException("Failed to upload photo: " + e.getMessage());

        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    @Override
    public void delete(String url) {
        //TODO
    }
}
