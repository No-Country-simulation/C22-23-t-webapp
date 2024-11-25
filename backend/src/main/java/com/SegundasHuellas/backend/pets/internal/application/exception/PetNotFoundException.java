package com.SegundasHuellas.backend.pets.internal.application.exception;

public class PetNotFoundException extends RuntimeException{
    public PetNotFoundException(Long id) {
        super("Pet with ID " + id + " not found");
    }
}
