package com.SegundasHuellas.backend.shared.application.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Objects;


@Schema(description = "Generic paginated response wrapper")
public class PageResponse<T> {
    @Schema(
            description = "List of items in the current page"
    )
    private final List<T> content;
    @Schema(
            description = "Pagination information and metadata"
    )
    private final PaginationMetadata pagination;

    public PageResponse(

            @Schema(
                    description = "List of items in the current page"
            )
            List<T> content,

            @Schema(
                    description = "Pagination information and metadata"
            )
            PaginationMetadata pagination
    ) {
        this.content = content;
        this.pagination = pagination;
    }

    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                new PaginationMetadata(
                        page.getNumber(),
                        page.getSize(),
                        page.getTotalElements(),
                        page.getTotalPages(),
                        page.isFirst(),
                        page.isLast()
                )
        );
    }

    @Schema(
            description = "List of items in the current page"
    )
    public List<T> content() {
        return content;
    }

    @Schema(
            description = "Pagination information and metadata"
    )
    public PaginationMetadata pagination() {
        return pagination;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PageResponse) obj;
        return Objects.equals(this.content, that.content) &&
               Objects.equals(this.pagination, that.pagination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, pagination);
    }

    @Override
    public String toString() {
        return "PageResponse[" +
               "content=" + content + ", " +
               "pagination=" + pagination + ']';
    }


    @Schema(description = "Metadata containing pagination details")
    public record PaginationMetadata(
            @Schema(
                    description = "Current page number (0-based)",
                    example = "0",
                    minimum = "0"
            )
            int pageNumber,

            @Schema(
                    description = "Number of items per page",
                    example = "20",
                    minimum = "1"
            )
            int pageSize,


            @Schema(
                    description = "Total number of items across all pages",
                    example = "100"
            )
            long totalElements,

            @Schema(
                    description = "Total number of pages",
                    example = "5",
                    minimum = "0"
            )
            int totalPages,

            @Schema(
                    description = "Indicates if this is the first page",
                    example = "true"
            )
            boolean first,

            @Schema(
                    description = "Indicates if this is the last page",
                    example = "false"
            )
            boolean last
    ) {
    }
}
