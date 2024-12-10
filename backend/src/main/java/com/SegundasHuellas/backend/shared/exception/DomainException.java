package com.SegundasHuellas.backend.shared.exception;


import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public class DomainException extends RuntimeException {
    private final ErrorCodeProvider errorCode;
    private final String[] parameters;
    private final boolean useCustomMessage;

    // Used when the error message will be built from messages.properties
    public DomainException(ErrorCodeProvider errorCode, String... parameters) {
        this.errorCode = errorCode;
        this.parameters = parameters;
        this.useCustomMessage = false;
    }

    // Private constructor for custom messages
    private DomainException(ErrorCodeProvider errorCode, String customMessage, boolean useCustomMessage) {
        super(customMessage);
        this.errorCode = errorCode;
        this.parameters = new String[0];
        this.useCustomMessage = true;
    }

    public boolean usesCustomMessage() {
        return useCustomMessage;
    }

    // Static factory method for custom messages
    public static DomainException withCustomMessage(ErrorCodeProvider errorCode, String customMessage) {
        return new DomainException(errorCode, customMessage, true);
    }

    public ErrorCodeProvider getErrorCode() {
        return errorCode;
    }

    public String[] getParameters() {
        return parameters;
    }

    public enum ErrorCode implements ErrorCodeProvider {
        GENERAL,
        CONSTRAINT_VIOLATION(BAD_REQUEST),
        DATA_TYPE_MISMATCH(BAD_REQUEST),
        RESOURCE_NOT_FOUND(NOT_FOUND),
        DUPLICATED_DATA(CONFLICT),
        OPERATION_NOT_ALLOWED(METHOD_NOT_ALLOWED),
        INVALID_DATA(BAD_REQUEST),
        INVALID_STATE(BAD_REQUEST),
        INVALID_AGE(BAD_REQUEST);

        public final HttpStatus statusCode;

        ErrorCode() {
            this(INTERNAL_SERVER_ERROR);
        }

        ErrorCode(HttpStatus statusCode) {
            this.statusCode = statusCode;
        }

        @Override
        public HttpStatus getStatus() {
            return this.statusCode;
        }

    }
}
