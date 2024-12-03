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
        // Base JPQL query to get pets
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

        // Query to count the total number of results (necessary for pagination)
        String countJpql = """
                SELECT COUNT(DISTINCT p)
                FROM Pet p
                """;
        // List that stores the dynamic conditions of the search.
        ArrayList<String> jpqlParts = new ArrayList<>();
        //Condition that is always true to avoid problems with the WHERE clause when there are no filters.
        jpqlParts.add("1=1");
        //Map that stores the values of the parameters that will be used in the query.
        Map<String, Object> params = new HashMap<>();

        //For each search criterion present, we add the corresponding JPQL condition.
        //And we store its value in the parameter map.
        if (searchCriteria.name() != null) {
            // Case-insensitive partial match for the name.
            jpqlParts.add("UPPER(p.name) LIKE UPPER(:name)");
            params.put("name", "%" + searchCriteria.name() + "%");
        }

        if (searchCriteria.species() != null) {
            // Exact match for the species.
            jpqlParts.add("p.breed.species = :species");
            params.put("species", searchCriteria.species());
        }

        if (searchCriteria.breed() != null) {
            // Case-insensitive partial match for the breed.
            jpqlParts.add("UPPER(p.breed.name) LIKE UPPER(:breed)");
            params.put("breed", "%" + searchCriteria.breed() + "%");
        }

        if (searchCriteria.status() != null) {
            // Exact match for the status.
            jpqlParts.add("p.status = :status");
            params.put("status", searchCriteria.status());
        }

        //Join all the conditions with an AND to form the complete WHERE clause.
        String whereClause = String.join(" AND ", jpqlParts);

        //Execute the count query to know the total number of results.
        var countQuery = entityManager.createQuery(countJpql + " WHERE " + whereClause, Long.class);

        //Inject the values of the parameters in the query.
        params.forEach(countQuery::setParameter);
        Long total = countQuery.getSingleResult();

        //Build and execute the main query with the same criteria.
        var dataQuery = entityManager.createQuery(
                jpql + " WHERE " + whereClause + buildOrderByClause(pageable, "p"),
                PetSearchResult.class);
        params.forEach(dataQuery::setParameter);

        //Pagination configuration.
        dataQuery.setFirstResult((int) pageable.getOffset());
        dataQuery.setMaxResults(pageable.getPageSize());

        //Get the results and build the Page object with the pagination information.
        List<PetSearchResult> results = dataQuery.getResultList();
        return PageResponse.from(new PageImpl<>(results, pageable, total));
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
