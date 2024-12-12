package com.SegundasHuellas.backend.adoptions;

import com.SegundasHuellas.backend.shared.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "adoptions")
@SuperBuilder
public class Adoption extends BaseEntity {

    @Column(name = "pet_id")
    private Long petId;

    @Column(name = "adopter_id")
    private Long adopterId;

    @Column(name = "provider_id")
    private Long providerId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AdoptionStatus status;


}
