package com.SegundasHuellas.backend.pets.internal.domain.vo;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

/*
 Se puede complicar aún más, por ejemplo con objeto tipo Vaccine
 en lugar de String, que puede contener por ej., la fecha de vacunación, expiración, etc. (No lo veo necesario aún)
* */

@Embeddable
@EqualsAndHashCode
@ToString
@Getter
public class VaccinationStatus {

    @Column(name = "is_vaccinated")
    private boolean isVaccinated;

    @ElementCollection(fetch = FetchType.EAGER) // Revisar luego si es necesario hacer eager fetch
    @CollectionTable(
            name = "pet_vaccines",
            joinColumns = @JoinColumn(name = "pet_id")
    )
    @Column(name = "vaccine")
    private Set<String> vaccines = new HashSet<>();

    protected VaccinationStatus() {
        this.isVaccinated = false;
        this.vaccines = new HashSet<>();
    }

    private VaccinationStatus(boolean isVaccinated, Set<String> vaccines) {
        this.isVaccinated = isVaccinated;
        this.vaccines = new HashSet<>(vaccines);
    }

    public static VaccinationStatus vaccinated(Set<String> vaccines) {
        if (vaccines == null || vaccines.isEmpty()) {
            throw new IllegalArgumentException("Vaccinated status requires at least one vaccine");
        }

        return new VaccinationStatus(true, vaccines);
    }

    public static VaccinationStatus notVaccinated() {
        return new VaccinationStatus(false, new HashSet<>());
    }

    public Set<String> getVaccines() {
        return Collections.unmodifiableSet(vaccines);
    }

    public void addVaccine(String vaccine) {
        Objects.requireNonNull(vaccine, "A vaccine name is required");
        if (vaccine.trim().isEmpty()) {
            throw new IllegalArgumentException("Vaccine name cannot be empty");
        }

        this.vaccines.add(vaccine.trim());
        this.isVaccinated = true;
    }

    public void removeVaccine(String vaccine) {
        this.vaccines.remove(vaccine);

        if (this.vaccines.isEmpty()) {
            this.isVaccinated = false;
        }
    }

    public void addVaccines(Collection<String> newVaccines) {
        Objects.requireNonNull(newVaccines, "Vaccines collection cannot be null");
        newVaccines.forEach(this::addVaccine);
    }

    public boolean hasVaccine(String vaccine) {
        return vaccines.contains(vaccine);
    }

    public boolean hasAllVaccines(Collection<String> requiredVaccines) {
        return vaccines.containsAll(requiredVaccines);
    }

}


