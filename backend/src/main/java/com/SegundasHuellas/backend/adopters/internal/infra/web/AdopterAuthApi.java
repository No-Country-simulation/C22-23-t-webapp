package com.SegundasHuellas.backend.adopters.internal.infra.web;

import com.SegundasHuellas.backend.adopters.internal.application.dto.AdopterDetailsResponse;
import com.SegundasHuellas.backend.adopters.internal.application.dto.AdopterRegistrationRequest;
import com.SegundasHuellas.backend.adopters.internal.application.dto.AdopterSummaryResponse;
import com.SegundasHuellas.backend.adopters.internal.application.dto.AdopterUpdateRequest;
import com.SegundasHuellas.backend.auth.api.dto.AuthenticationResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Adopter Authentication", description = "API endpoints for adopter registration and management")
public interface AdopterAuthApi {


    @Operation(
            summary = "Register new adopter",
            description = "Creates a new adopter account with basic profile information"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Adopter successfully registered",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthenticationResponse.class)
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
    public AuthenticationResponse register(@RequestBody @Valid AdopterRegistrationRequest request);


    @Operation(
            summary = "Get adopter details",
            description = "Retrieves detailed information about a specific adopter"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Adopter details retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdopterDetailsResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Adopter not found",
                    content = @Content
            )
    })
    @GetMapping("/{userId}")
    public AdopterDetailsResponse getAdopterDetails(@PathVariable(name = "userId") Long userId);

    @Operation(
            summary = "List adopters",
            description = "Retrieves a paginated list of adopters with summary information"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Adopters list retrieved successfully",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = AdopterSummaryPageResponse.class)
            )
    )
    @GetMapping
    public PageResponse<AdopterSummaryResponse> getAdopters(
            @Parameter(
                    description = "Pagination parameters (page, size, sort)",
                    example = "?page=0&size=20&sort=createdAt,desc"
            )
            Pageable pageable);


    @Operation(
            summary = "Update adopter profile",
            description = "Updates an adopter's profile information. All fields are optional but cannot be blank if provided."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Adopter updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid update data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Adopter not found",
                    content = @Content
            )
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAdopter(@PathVariable(name = "id") Long id,
                              @RequestBody @Valid AdopterUpdateRequest request);
}


@Schema(name = "AdopterSummaryPageResponse", description = "Paginated list of adopter summaries")
@Getter
@Setter
class AdopterSummaryPageResponse extends PageResponse<AdopterSummaryResponse> {

    @Schema(description = "List of adopter summaries")
    private List<AdopterSummaryResponse> content;

    @Schema(description = "Pagination metadata")
    private PaginationMetadata pagination;

    public AdopterSummaryPageResponse(List<AdopterSummaryResponse> content, PaginationMetadata pagination) {
        super(content, pagination);
        this.content = content;
        this.pagination = pagination;
    }
}
