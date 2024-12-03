package com.SegundasHuellas.backend.shared.exception;

public class ImageOperationException extends DomainException {

    public ImageOperationException(String message) {
        super(ErrorCode.INVALID_STATE, message);//TODO: cambiar error code
    }
}
