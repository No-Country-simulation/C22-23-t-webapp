package com.SegundasHuellas.backend.adopters.internal.infra.web;

import com.SegundasHuellas.backend.shared.application.dto.ImageMetadata;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Adopter Photos", description = "API endpoints for managing adopter profile photos")
public interface AdopterPhotoApi {


    @Operation(
            summary = "Upload profile photo",
            description = "Uploads a profile photo for the specified adopter"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Photo successfully uploaded",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ImageMetadata.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid image format or size",
                    content = @Content


            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Adopter not found",
                    content = @Content
            )
    })
    @PostMapping(
            value = "/{id}/photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    ImageMetadata uploadImage(
            @PathVariable
            @Parameter(
                    description = "Adopter's unique identifier",
                    example = "1234"
            )
            Long id,

            @RequestParam("image")
            @Parameter(
                    description = "Image file to upload (JPG, PNG, or WebP). Max size: 3MB",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE),
                    schema = @Schema(type = "string", format = "binary")
            )
            MultipartFile image
    );

    @Operation(
            summary = "Delete profile photo",
            description = "Removes the current profile photo of the specified adopter. The default profile photo will be assigned."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Photo successfully deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Adopter not found or no photo exists"

            )
    })
    @DeleteMapping("/{id}/photo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteImage(
            @PathVariable
            @Parameter(
                    description = "Adopter's unique identifier",
                    example = "1234"
            )
            Long id
    );
}

