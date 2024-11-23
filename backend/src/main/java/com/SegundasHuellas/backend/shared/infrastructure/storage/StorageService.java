package com.SegundasHuellas.backend.shared.infrastructure.storage;

import com.SegundasHuellas.backend.shared.application.dto.ImageMetadata;
import com.SegundasHuellas.backend.shared.infrastructure.storage.config.UploadConfig;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    ImageMetadata upload(MultipartFile image, UploadConfig config);

    void delete(String url);

}
