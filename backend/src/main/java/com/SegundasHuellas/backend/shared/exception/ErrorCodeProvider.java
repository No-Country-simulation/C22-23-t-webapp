package com.SegundasHuellas.backend.shared.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCodeProvider {
    HttpStatus getStatus();
    String name();
}
