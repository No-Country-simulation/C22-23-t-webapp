package com.SegundasHuellas.backend.petProviders.internal.domain.entity;

import com.SegundasHuellas.backend.auth.internal.domain.entity.User;
import com.SegundasHuellas.backend.petProviders.internal.domain.enums.PetProviderStatus;
import com.SegundasHuellas.backend.petProviders.internal.domain.enums.PetProviderType;
import com.SegundasHuellas.backend.shared.domain.base.BaseEntity;
import com.SegundasHuellas.backend.shared.domain.vo.Address;
import com.SegundasHuellas.backend.shared.domain.vo.Image;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;


@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "pet_providers")
public class PetProvider extends BaseEntity {

    //name
    @Column(name = "name", nullable = false)
    private String name;

    //foto de perfil
    @Embedded
    @Column(name = "photo")
    private Image photo;

    //Dirección
    @Embedded
    @Column(name = "address")
    private Address address;

    //Descripcion
    @Column(name = "description")
    private String description;

    //Telefono
    @Column(name = "phone_number")
    private String phoneNumber;

    //Enum si es rescatista o refugio
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PetProviderType type;


    //Status del petProvider si esta habilitado o no.
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PetProviderStatus status;

    //Lista de mascotas
    @ElementCollection
    @CollectionTable(
            name = "pet_provider_pets",
            joinColumns = @JoinColumn(name = "pet_provider_id")
    )
    @Column(name = "pet_id")
    private Set<Long> pets = new HashSet<>();

    //Lista de solicitudes de adopcion recibidas.
    @ElementCollection
    @CollectionTable(
            name = "pet_provider_adoption_applications",
            joinColumns = @JoinColumn(name = "pet_provider_id")
    )
    @Column(name = "adoption_application_id")
    private Set<Long> adoptionApplications = new HashSet<>();

    @Column(name = "user_id")
    private Long userId;

    public void registerAdoptionApplication(Long applicationId){
        this.adoptionApplications.add(applicationId);
    }

    public void addPet(Long petId) {
        pets.add(petId);
    }

    public void addPet(Set<Long> petIds) {
        pets.addAll(petIds);
    }

    public void removePet(Long petId) {
        pets.removeIf(id -> id.equals(petId));
    }

    public void setStreet(String street) {
        this.address.setStreet(street);
    }

    public void setCity(String city) {
        this.address.setCity(city);
    }

    public void setState(String state) {
        this.address.setState(state);
    }

    public void setZip(String zip) {
        this.address.setZip(zip);
    }

    public void setCountry(String country) {
        this.address.setCountry(country);
    }

    /**
     * Calculate the profile completion score based on available attributes.
     *
     * @return the profile completion score, capped at 10
     */
    public int getProfileCompletionScore() {
        int score = 0;

        // Check if name is provided
        if (name != null && !name.isEmpty()) score++;

        // Check if phone number is provided
        if (phoneNumber != null && !phoneNumber.isEmpty()) score++;

        // Check address details
        if (address != null) {
            if (address.getStreet() != null && !address.getStreet().isEmpty()) score++;
            if (address.getCity() != null && !address.getCity().isEmpty()) score++;
            if (address.getState() != null && !address.getState().isEmpty()) score++;
            if (address.getZip() != null && !address.getZip().isEmpty()) score++;
            if (address.getCountry() != null && !address.getCountry().isEmpty()) score++;
        }

        // Check if there are any pets listed
        if (pets != null && !pets.isEmpty()) score++;

        // Return the score, ensuring it does not exceed 10
        return Math.min(10, score);
    }
}
