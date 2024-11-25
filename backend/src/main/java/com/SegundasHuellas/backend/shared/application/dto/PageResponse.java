package com.SegundasHuellas.backend.shared.application.dto;


import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Wrapper genérico para respuestas paginadas.
 *
 * @param <T> Tipo de elementos en el contenido de la página
 * @param content Lista de elementos en la página actual
 * @param pagination Metadatos sobre el estado de la paginación
 */
public record PageResponse<T>(
        List<T> content,
        PaginationMetadata pagination
) {

    /**
     * Crea un PageResponse a partir de un objeto Page de Spring Data.
     *
     * @param <T> Tipo de elementos en la página
     * @param page Objeto Page de Spring Data a convertir
     * @return Un nuevo PageResponse conteniendo los datos y metadatos de la página
     */
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

    /**
     * Contiene metadatos sobre el estado de la paginación.
     *
     * @param pageNumber Número de página actual (base 0)
     * @param pageSize Cantidad de elementos por página
     * @param totalElements Número total de elementos en todas las páginas
     * @param totalPages Número total de páginas
     * @param first Indica si es la primera página
     * @param last Indica si es la última página
     */
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
