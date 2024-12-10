package com.SegundasHuellas.backend.pets.internal.infra.persistence;

import com.SegundasHuellas.backend.pets.internal.application.dto.PetSearchCriteria;
import com.SegundasHuellas.backend.pets.internal.application.dto.PetSearchResult;
import com.SegundasHuellas.backend.shared.application.dto.PageResponse;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.INVALID_DATA;

/**
 * Repository class for performing dynamic and paginated searches on the {@code Pet} entity.
 * <p>
 * This class uses JPQL queries and dynamic filters to search pets based on criteria such as
 * name, species, breed, and status. It also supports pagination and sorting.
 * </p>
 *
 * <b>Features:</b>
 * - Dynamic JPQL query building based on search criteria.
 * - Count query for accurate pagination metadata.
 * - Sorting support based on pageable information.
 *
 */
@Repository
@RequiredArgsConstructor
public class PetSearchQuery {

    private static final Logger log = LoggerFactory.getLogger(PetSearchQuery.class);
    private final EntityManager entityManager;

    /**
     * Searches for pets based on the specified search criteria, with support for pagination and sorting.
     * <p>
     * This method dynamically builds a JPQL query using the provided {@link PetSearchCriteria} and executes it
     * against the database. The results are returned in a paginated format, along with metadata.
     * </p>
     *
     * <b>Search Filters:</b>
     * - Name: Partial match (case-insensitive).
     * - Species: Exact match.
     * - Breed: Partial match (case-insensitive).
     * - Status: Exact match.
     *
     * <b>Sorting:</b> The results can be sorted based on any valid field defined in the {@code Pet} entity.
     *
     * @param searchCriteria The criteria to filter the pets by.
     * @param pageable       The pagination and sorting information.
     * @return A {@link PageResponse} containing the search results and pagination metadata.
     */
    public PageResponse<PetSearchResult> pageSearch(PetSearchCriteria searchCriteria, Pageable pageable) {
        String baseQuery = " FROM Pet p";
        StringBuilder queryBuilder = new StringBuilder("SELECT new com.SegundasHuellas.backend.pets.internal.application.dto.PetSearchResult(")
                .append("p.id, p.name, p.breed.species, p.age.valueInDays, p.gender, p.status, p.size, p.mainPhoto)").append(baseQuery);
        StringBuilder countBuilder = new StringBuilder("SELECT COUNT(p)").append(baseQuery);
        Map<String, Object> params = new HashMap<>();
        List<String> conditions = new ArrayList<>();

        if (searchCriteria.name() != null) {
            conditions.add("UPPER(p.name) LIKE UPPER(:name)");
            params.put("name", "%" + searchCriteria.name() + "%");
        }
        if (searchCriteria.species() != null) {
            conditions.add("p.breed.species = :species");
            params.put("species", searchCriteria.species());
        }
        if (searchCriteria.breed() != null) {
            conditions.add("UPPER(p.breed.name) LIKE UPPER(:breed)");
            params.put("breed", "%" + searchCriteria.breed() + "%");
        }
        if (searchCriteria.status() != null) {
            conditions.add("p.status = :status");
            params.put("status", searchCriteria.status());
        }
        if (searchCriteria.size() != null) {
            conditions.add("p.size = :size");
            params.put("size", searchCriteria.size());
        }
        if (searchCriteria.gender() != null) {
            conditions.add("p.gender = :gender");
            params.put("gender", searchCriteria.gender());
        }
        if (searchCriteria.minAge() != null) {
            conditions.add("p.age.valueInDays >= :minAge");
            params.put("minAge", searchCriteria.minAge().getValueInDays());
        }
        if (searchCriteria.maxAge() != null) {
            conditions.add("p.age.valueInDays <= :maxAge");
            params.put("maxAge", searchCriteria.maxAge().getValueInDays());
        }

        if (!conditions.isEmpty()) {
            String whereClause = String.join(" AND ", conditions);
            queryBuilder.append(" WHERE ").append(whereClause);
            countBuilder.append(" WHERE ").append(whereClause);
        }

        try {
            var countJpqlQuery = entityManager.createQuery(countBuilder.toString(), Long.class);
            var dataJpqlQuery = entityManager.createQuery(queryBuilder.toString() + buildOrderByClause(pageable, "p"), PetSearchResult.class);

            params.forEach(countJpqlQuery::setParameter);
            params.forEach(dataJpqlQuery::setParameter);

            Long total = countJpqlQuery.getSingleResult();
            dataJpqlQuery.setFirstResult((int) pageable.getOffset());
            dataJpqlQuery.setMaxResults(pageable.getPageSize());

            List<PetSearchResult> results = dataJpqlQuery.getResultList();
            return PageResponse.from(new PageImpl<>(results, pageable, total));
        } catch (Exception e) {
            log.error("Error executing search query", e);
            throw new DomainException(INVALID_DATA, e.getMessage());
        }
    }

    /**
     * Builds the ORDER BY clause for the JPQL query.
     * The clause is built using the pagination information and the entity name.
     * @param pageable   The pagination information.
     * @param entityName The name of the entity.
     * @return The built ORDER BY clause.
     */
    private String buildOrderByClause(Pageable pageable, String entityName) {
        // If the pagination information defines a sort, we build the ORDER BY clause.
        if (pageable.getSort().isSorted()) {
            // We stream over the sort orders and map each one to a string of the form
            // "entityName.propertyName direction".
            // We then join the strings with a comma and a space, and prepend "ORDER BY ".
            return pageable.getSort().stream()
                    .map(order -> entityName + "." + order.getProperty() + " " + order.getDirection())
                    .collect(Collectors.joining(", ", " ORDER BY ", ""));
        }

        // If there is no sort, we return an empty string, which is the default.
        return "";
    }
}
