package com.SegundasHuellas.backend.pets.internal.domain.vo;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

/**
 * Represents the vaccination status of a pet.
 * <p>
 * This value object tracks whether a pet is vaccinated and maintains a collection of vaccine names.
 * </p>
 *
 *
 */

@Embeddable
@EqualsAndHashCode
@ToString
@Getter
public class VaccinationStatus {

    /**
     * Indicates whether the pet has been vaccinated.
     */
    @Column(name = "is_vaccinated")
    private boolean isVaccinated;

    /**
     * Collection of vaccine names the pet has received.
     */
    @ElementCollection(fetch = FetchType.EAGER) // Revisar luego si es necesario hacer eager fetch
    @CollectionTable(
            name = "pet_vaccines",
            joinColumns = @JoinColumn(name = "pet_id")
    )
    @Column(name = "vaccine")
    private Set<String> vaccines = new HashSet<>();

    /**
     * Protected constructor for JPA.
     * Sets default values for an unvaccinated pet with no vaccines.
     */
    protected VaccinationStatus() {
        this.isVaccinated = false;
        this.vaccines = new HashSet<>();
    }

    /**
     * Private constructor for controlled instantiation.
     *
     * @param isVaccinated boolean indicating whether the pet is vaccinated.
     * @param vaccines     Set of vaccine names.
     */
    private VaccinationStatus(boolean isVaccinated, Set<String> vaccines) {
        this.isVaccinated = isVaccinated;
        this.vaccines = new HashSet<>(vaccines);
    }

    /**
     * Creates a vaccination status for a vaccinated pet.
     *
     * @param vaccines Set of vaccine names (must not be null or empty).
     * @return a new instance of {@code VaccinationStatus}.
     * @throws IllegalArgumentException if vaccines are null or empty.
     */
    public static VaccinationStatus vaccinated(Set<String> vaccines) {
        if (vaccines == null || vaccines.isEmpty()) {
            throw new IllegalArgumentException("Vaccinated status requires at least one vaccine");
        }

        return new VaccinationStatus(true, vaccines);
    }

    /**
     * Creates a vaccination status for an unvaccinated pet.
     *
     * @return a new instance of {@code VaccinationStatus}.
     */
    public static VaccinationStatus notVaccinated() {
        return new VaccinationStatus(false, new HashSet<>());
    }

    /**
     * Retrieves an unmodifiable set of vaccines.
     *
     * @return Set of vaccine names.
     */
    public Set<String> getVaccines() {
        return Collections.unmodifiableSet(vaccines);
    }

    /**
     * Adds a vaccine to the collection and updates the vaccinated status.
     *
     * @param vaccine Name of the vaccine (must not be null or empty).
     * @throws IllegalArgumentException if the vaccine name is null or empty.
     */
    public void addVaccine(String vaccine) {
        Objects.requireNonNull(vaccine, "A vaccine name is required");
        if (vaccine.trim().isEmpty()) {
            throw new IllegalArgumentException("Vaccine name cannot be empty");
        }

        this.vaccines.add(vaccine.trim());
        this.isVaccinated = true;
    }

    /**
     * Removes a vaccine from the collection and updates the vaccinated status.
     *
     * @param vaccine Name of the vaccine to remove.
     */
    public void removeVaccine(String vaccine) {
        this.vaccines.remove(vaccine);

        if (this.vaccines.isEmpty()) {
            this.isVaccinated = false;
        }
    }

    /**
     * Adds multiple vaccines to the collection.
     *
     * @param newVaccines Collection of vaccine names (must not be null).
     */
    public void addVaccines(Collection<String> newVaccines) {
        Objects.requireNonNull(newVaccines, "Vaccines collection cannot be null");
        newVaccines.forEach(this::addVaccine);
    }

    /**
     * Checks if the pet has received a specific vaccine.
     *
     * @param vaccine Name of the vaccine.
     * @return true if the vaccine is in the collection, false otherwise.
     */
    public boolean hasVaccine(String vaccine) {
        return vaccines.contains(vaccine);
    }

    /**
     * Checks if the pet has received all required vaccines.
     *
     * @param requiredVaccines Collection of vaccine names.
     * @return true if all required vaccines are present, false otherwise.
     */
    public boolean hasAllVaccines(Collection<String> requiredVaccines) {
        return vaccines.containsAll(requiredVaccines);
    }

}


