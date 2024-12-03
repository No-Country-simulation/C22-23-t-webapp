package com.SegundasHuellas.backend.shared.infrastructure.storage;

import com.SegundasHuellas.backend.shared.application.dto.ImageMetadata;
import com.SegundasHuellas.backend.shared.exception.ImageOperationException;
import com.SegundasHuellas.backend.shared.infrastructure.storage.config.UploadConfig;
import com.SegundasHuellas.backend.shared.infrastructure.storage.validation.FileValidator;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CloudinaryStorageServiceTest {

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @Mock
    private FileValidator fileValidator;

    @InjectMocks
    private CloudinaryStorageService cloudinaryStorageService;

    private static MockMultipartFile createMockPhoto() {
        return new MockMultipartFile(
                "test-photo.jpg",
                "test-photo.jpg",
                "image/jpeg",
                "some image content".getBytes()
        );
    }

    @BeforeEach
    void setUp() {
        when(cloudinary.uploader()).thenReturn(uploader);
    }

    @Test
    @DisplayName("""
            Photo upload should return correct ImageMetadata when upload is successful
            """)
    void testUploadSuccess() throws IOException {
        MockMultipartFile mockPhoto = createMockPhoto();

        UploadConfig uploadConfig = UploadConfig.builder()
                                                .options(Map.of("folder", "test-folder"))
                                                .build();


        when(uploader.upload(any(File.class), any(Map.class)))
                .thenReturn(createValidCloudinaryResponse());

        ImageMetadata result = cloudinaryStorageService.upload(mockPhoto, uploadConfig);

        assertThat(result)
                .isNotNull()
                .satisfies(metadata -> {
                    assertThat(metadata.url()).isEqualTo("https://cloudinary.com/test-photo.jpg");
                    assertThat(metadata.dimensions().width()).isEqualTo(800);
                    assertThat(metadata.dimensions().height()).isEqualTo(600);
                    assertThat(metadata.bytes()).isEqualTo(12345L);
                    assertThat(metadata.readableSize()).isEqualTo("12.06 KB");
                    assertThat(metadata.format()).isEqualTo(ImageMetadata.ImageFormat.JPG);
                });

        verify(fileValidator).validate(mockPhoto);
    }

    @Test
    @DisplayName("""
            Photo upload should throw a PhotoUploadException when upload fails
            """)
    void testUploadFailure() throws IOException {
        MockMultipartFile mockPhoto = createMockPhoto();

        UploadConfig uploadConfig = UploadConfig.builder().options(Map.of()).build();

        when(uploader.upload(any(File.class), any(Map.class)))
                .thenThrow(new IOException("Upload failed"));

        assertThatThrownBy(() -> cloudinaryStorageService.upload(mockPhoto, uploadConfig))
                .isInstanceOf(ImageOperationException.class);

    }

    private Map<String, Object> createValidCloudinaryResponse() {
        return Map.of(
                "public_id", "test-public-id",
                "secure_url", "https://cloudinary.com/test-photo.jpg",
                "width", 800,
                "height", 600,
                "bytes", 12345,
                "format", "jpg"

        );
    }
}