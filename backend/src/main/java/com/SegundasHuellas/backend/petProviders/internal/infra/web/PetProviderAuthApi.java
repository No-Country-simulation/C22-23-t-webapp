package com.SegundasHuellas.backend.petProviders.internal.infra.web;

import com.SegundasHuellas.backend.auth.api.dto.AuthenticationResponse;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderDetailsResponse;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderRegistrationRequest;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderSummaryResponse;
import com.SegundasHuellas.backend.petProviders.internal.application.dto.PetProviderUpdateRequest;
import com.SegundasHuellas.backend.shared.application.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "PetProvider Authentication", description = "API endpoints for adopter registration adn management")
public interface PetProviderAuthApi {


    @Operation(
            summary = "Register new pet provider",
            description = "Registers a new pet provider"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Pet provider successfully registered",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PetProviderDetailsResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid registration data or passwords don't match",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email already registered",
                    content = @Content
            )
    })
    @PostMapping("/register")
    public AuthenticationResponse registerPetProvider (@RequestBody @Valid PetProviderRegistrationRequest request);


    @Operation(
            summary = "Get pet provider details",
            description = "Retrieves detailed information about a specific pet provider"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pet provider details retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PetProviderDetailsResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pet provider not found",
                    content = @Content
            )
    })
    @GetMapping("/{userId}")
    public PetProviderDetailsResponse getPetProviderDetails(Long id);


    @Operation(
            summary = "Get all pet providers",
            description = "Retrieves a page of all pet providers"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pet providers retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PetProviderSummaryResponse.class)
                    )
            )
    })
    @GetMapping
    public PageResponse<PetProviderSummaryResponse> getAllPetProviders(
            @Parameter(
                    description = "Pagination parameters (page, size, sort)",
                    example = "?page=0&size=20&sort=createdAt,desc"
            )
            Pageable pageable
    );


    @Operation(
            summary = "Update pet provider details",
            description = "Updates the details of an existing pet provider"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Pet provider details updated successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid update data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pet provider not found",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    public void updatePetProvider(@PathVariable(name = "id") Long id,
                                  @RequestBody @Valid PetProviderUpdateRequest request);
}

@Schema(name = "PetProviderSummaryPageResponse", description = "Paginated list of adopter summaries")
@Getter
@Setter
class PetProviderSummaryPageResponse extends PageResponse<PetProviderSummaryResponse> {

    @Schema(description = "List of pet provider summaries")
    private List<PetProviderSummaryResponse> content;

    @Schema(description = "Pagination metadata")
    private PaginationMetadata pagination;

    public PetProviderSummaryPageResponse(List<PetProviderSummaryResponse> content, PaginationMetadata pagination) {
        super(content, pagination);
        this.content = content;
        this.pagination = pagination;
    }
}
