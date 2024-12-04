package com.SegundasHuellas.backend.adopters.internal.domain.entity;

import com.SegundasHuellas.backend.adopters.internal.domain.enums.AdopterStatus;
import com.SegundasHuellas.backend.adopters.internal.domain.vo.PetPreference;
import com.SegundasHuellas.backend.shared.domain.base.BaseEntity;
import com.SegundasHuellas.backend.shared.domain.vo.Address;
import com.SegundasHuellas.backend.shared.exception.DomainException;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.INVALID_STATE;

@Entity
@Table(name = "adopters")
public class Adopter extends BaseEntity {

    public static final int MAX_ACTIVE_APPLICATIONS = 3;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "bio", length = 1000)
    private String bio;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AdopterStatus status;

    @ElementCollection
    @CollectionTable(name = "adopter_preferences")
    private Set<PetPreference> petPreferences = new HashSet<>();

    @ElementCollection
    @CollectionTable(
            name = "adopter_adoption_applications",
            joinColumns = @JoinColumn(name = "adopter_id")
    )
    @Column(name = "adoption_application_id")
    private Set<Long> adoptionApplicationIds = new HashSet<>();

    public void addAdoptionApplication(Long applicationId) {

        if (adoptionApplicationIds.size() >= MAX_ACTIVE_APPLICATIONS) {
            throw new DomainException(INVALID_STATE,
                    "Max number of applications reached for adopter: " + MAX_ACTIVE_APPLICATIONS);
        }
        adoptionApplicationIds.add(applicationId);
    }

    public void removeAdoptionApplication(Long applicationId) {
        adoptionApplicationIds.remove(applicationId);
    }

    public boolean hasMaxApplications() {
        return adoptionApplicationIds.size() >= MAX_ACTIVE_APPLICATIONS;
    }

    public boolean hasActiveApplication(Long applicationId) {
        return adoptionApplicationIds.contains(applicationId);
    }
}