package com.SegundasHuellas.backend.pets.internal.application.exception;

public class InvalidPetDataException extends RuntimeException{
    public InvalidPetDataException(String message) {
        super(message);
    }
}
