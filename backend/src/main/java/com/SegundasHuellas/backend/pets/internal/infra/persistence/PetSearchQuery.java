package com.SegundasHuellas.backend.pets.internal.infra.persistence;

import com.SegundasHuellas.backend.pets.internal.application.dto.PetSearchCriteria;
import com.SegundasHuellas.backend.pets.internal.application.dto.PetSearchResult;
import com.SegundasHuellas.backend.shared.application.dto.PageResponse;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Repositorio de búsqueda de mascotas
 * Se encarga de realizar la búsqueda de mascotas en la base de datos
 * y filtrar los resultados según los criterios de búsqueda, de manera dinámica
 */

@Repository
@RequiredArgsConstructor
public class PetSearchQuery {

    private final EntityManager entityManager;

    public PageResponse<PetSearchResult> pageSearch(PetSearchCriteria searchCriteria, Pageable pageable) {
        // Consulta base JPQL para obtener mascotas
        String jpql = """
                SELECT new com.SegundasHuellas.backend.pets.internal.application.dto.PetSearchResult(
                    p.id,
                    p.name,
                    p.breed.species,
                    p.age.valueInDays,
                    p.gender,
                    p.status,
                    p.photo
                )
                FROM Pet p
                """;

        // Consulta para contar el total de resultados (necesario para la paginación)
        String countJpql = """
                SELECT COUNT(DISTINCT p)
                FROM Pet p
                """;
        //Lista que almacena las condiciones dinámicas de la búsqueda.
        ArrayList<String> jpqlParts = new ArrayList<>();
        //Condición siempre verdadera para evitar problemas con el WHERE cuando no hay filtros.
        jpqlParts.add("1=1");
        //Mapa que almacenará los valores de los parámetros que se usarán en la consulta
        Map<String, Object> params = new HashMap<>();

        //Por cada criterio de búsqueda presente, agregamos la condicion JPQL correspondiente.
        //y guardamos su valor en el mapa de parámetros.
        if (searchCriteria.name() != null) {
            jpqlParts.add("UPPER(p.name) LIKE UPPER(:name)");
            params.put("name", "%" + searchCriteria.name() + "%");
        }

        if (searchCriteria.species() != null) {
            jpqlParts.add("p.breed.species = :species");
            params.put("species", searchCriteria.species());
        }

        if (searchCriteria.breed() != null) {
            jpqlParts.add("UPPER(p.breed.name) LIKE UPPER(:breed)");
            params.put("breed", "%" + searchCriteria.breed() + "%");
        }

        if (searchCriteria.status() != null) {
            jpqlParts.add("p.status = :status");
            params.put("status", searchCriteria.status());
        }

        //Une todas las condiciones con un AND para ormar la consulta WHERE completa.
        String whereClause = String.join(" AND ", jpqlParts);

        //Ejecuta la consulta de conteo para saber el total de resultados.
        var countQuery = entityManager.createQuery(countJpql + " WHERE " + whereClause, Long.class);

        //Inyecta los valores de los parámetros en la consulta.
        params.forEach(countQuery::setParameter);
        Long total = countQuery.getSingleResult();

        //Contruye y ejecuta la consulta principal con los mismos criterios.
        var dataQuery = entityManager.createQuery(
                jpql + " WHERE " + whereClause + buildOrderByClause(pageable, "p"),
                PetSearchResult.class);
        params.forEach(dataQuery::setParameter);

        //Configuración de la paginación.
        dataQuery.setFirstResult((int) pageable.getOffset());
        dataQuery.setMaxResults((pageable.getPageSize()));

        //Obtenemos los resultados y construimos el objeto Page con la información de paginación.
        List<PetSearchResult> results = dataQuery.getResultList();
        return PageResponse.from(new PageImpl<>(results, pageable, total));
    }

    private String buildOrderByClause(Pageable pageable, String entityName) {
        return pageable.getSort().isSorted()
                ? pageable.getSort().stream()
                          .map(order -> entityName + "." + order.getProperty() + " " + order.getDirection())
                          .collect(Collectors.joining(", ", " ORDER BY ", ""))
                : "";
    }
}
