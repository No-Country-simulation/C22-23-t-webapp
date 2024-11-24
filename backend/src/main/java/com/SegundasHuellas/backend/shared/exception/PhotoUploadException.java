package com.SegundasHuellas.backend.shared.exception;

public class PhotoUploadException extends DomainException {

    public PhotoUploadException(String message) {
        super(ErrorCode.INVALID_STATE, message);//TODO: cambiar error code
    }
}
