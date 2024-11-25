package com.SegundasHuellas.backend.shared.application.dto;


import org.springframework.data.domain.Page;

import java.util.List;


public record PageResponse<T>(
        List<T> content,
        PaginationMetadata pagination
) {

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

    public record PaginationMetadata(
            int pageNumber,
            int pageSize,
            long totalElements,
            int totalPages,
            boolean first,
            boolean last
    ) {
    }
}
