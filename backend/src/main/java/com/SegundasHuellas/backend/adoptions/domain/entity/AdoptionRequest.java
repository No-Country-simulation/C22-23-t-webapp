package com.SegundasHuellas.backend.adoptions.domain.entity;

import com.SegundasHuellas.backend.adopters.internal.domain.entity.Adopter;
import com.SegundasHuellas.backend.adoptions.domain.enums.AdoptionStatus;
import com.SegundasHuellas.backend.petProviders.internal.domain.entity.PetProvider;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Pet;
import com.SegundasHuellas.backend.shared.domain.base.BaseEntity;
import com.SegundasHuellas.backend.shared.domain.vo.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "adoptions")
public class AdoptionRequest extends BaseEntity {

    //email, firstName, lastName, phone, address, city, state, zip, country.

    @Column(name = "firstName")
    @NotBlank(message = "First name connot be blank")
    @Size(max = 50, message = "First name cannot be longer than 50 characters")
    private String firstName;

    @Column(name = "lastName")
    @NotBlank(message = "Last name connot be blank")
    @Size(max = 50, message = "Last name cannot be longer than 50 characters")
    private String lastName;

    @Column(name = "email")
    @NotBlank(message = "Email connot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "phone")
    @NotBlank(message = "Phone connot be blank")
    @Size(max = 20, message = "Phone cannot be longer than 20 characters")
    private String phone;

    @Column(name = "address")
    @Embedded
    private Address address;

    @Column(name = "status")
    private AdoptionStatus status = AdoptionStatus.PENDING_VERIFICATION;

    @Column(name = "comments")
    @NotEmpty(message = "Comments cannot be empty")
    @Size(max = 500, message = "Comments cannot be longer than 500 characters")
    private String comments;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "adopter_id")
    private Adopter adopter;

    @ManyToOne
    @JoinColumn(name = "pet_provider_id")
    private PetProvider petProvider;


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
}
