package com.SegundasHuellas.backend.petProviders.internal.domain.entity;

import com.SegundasHuellas.backend.petProviders.internal.domain.enums.PetProviderStatus;
import com.SegundasHuellas.backend.petProviders.internal.domain.enums.PetProviderType;
import com.SegundasHuellas.backend.shared.domain.base.BaseEntity;
import com.SegundasHuellas.backend.shared.domain.vo.Address;
import com.SegundasHuellas.backend.shared.domain.vo.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;


@Entity
@SuperBuilder
@Getter
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


    //Telefono
    @Column(name = "phone_number")
    private String phoneNumber;

    //Enum si es rescatista o refugio
    @Column(name = "type", nullable = false)
    private PetProviderType type;

    //Status del petProvider si esta habilitado o no.
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PetProviderStatus petProviderStatus;

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

    public void addPet(Long petId) {
        pets.add(petId);
    }

    public void removePet(Long petId) {
        pets.remove(petId);
    }

    public void addAdoptionApplication(Long applicationId) {
        adoptionApplications.add(applicationId);
    }

    public void removeAdoptionApplication(Long applicationId) {
        adoptionApplications.remove(applicationId);
    }
}
