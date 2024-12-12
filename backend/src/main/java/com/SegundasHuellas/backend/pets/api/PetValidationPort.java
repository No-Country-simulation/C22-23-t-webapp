package com.SegundasHuellas.backend.pets.api;

public interface PetValidationPort {

    boolean existsById(Long petId);
}
