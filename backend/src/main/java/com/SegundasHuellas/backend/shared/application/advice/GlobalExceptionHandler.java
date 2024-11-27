package com.SegundasHuellas.backend.shared.application.advice;

import com.SegundasHuellas.backend.shared.exception.DomainException;
import com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static com.SegundasHuellas.backend.shared.exception.DomainException.ErrorCode.*;
import static java.time.Instant.now;
import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_PREFIX = "error.";
    private final MessageSource messageSource;

    //Exceptions that won't be managed by the catch-all ExceptionHandler
    private final Set<Class<? extends Exception>> exceptionsToRethrow = Set.of(
            AccessDeniedException.class,
            IllegalStateException.class,
            SecurityException.class
    );
    @Value("${app.error.base-uri}")
    private String errorTypeBase;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ProblemDetail onNoSuchElementException(NoSuchElementException ex) {
        return handleException(RESOURCE_NOT_FOUND, ex);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail onHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        if (ex.getCause() instanceof InvalidFormatException ife) {
            return handleDataTypeError(ife);
        }
        return handleException(INVALID_DATA, ex);
    }

    //@Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail onValidationException(MethodArgumentNotValidException ex) {
        List<String> validationErrors = ex.getBindingResult().getFieldErrors().stream()
                                          .map(fieldError -> String.format("%s: %s",
                                                  fieldError.getField(),
                                                  fieldError.getDefaultMessage()))
                                          .toList();

        ProblemDetail pd = handleException(CONSTRAINT_VIOLATION, ex);
        pd.setProperty("errors", validationErrors);
        pd.setProperty("errorsCount", validationErrors.size());
        return pd;
    }

    @ExceptionHandler(DomainException.class)
    public ProblemDetail onDomainException(DomainException ex) {
        return handleException(
                ex.getErrorCode(),
                ex,
                ex.getParameters()
        );
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail onAnyOtherException(Exception exception) throws Exception {

        if (shouldRethrowException(exception)) {
            throw exception;
        }
        return handleException(GENERAL, exception);
    }

    private ProblemDetail handleDataTypeError(InvalidFormatException ife) {
        var fieldError = ife.getPath().isEmpty() ? "unknown" : ife.getPath().get(0).getFieldName();
        var invalidValue = ife.getValue() != null ? ife.getValue().toString() : "null";
        var targetType = ife.getTargetType().getSimpleName();

        return handleException(DATA_TYPE_MISMATCH, ife,
                fieldError, invalidValue, targetType);
    }

    private boolean shouldRethrowException(Exception exception) {
        return exceptionsToRethrow.stream()
                                  .anyMatch(exceptionClass -> exceptionClass.isInstance(exception));
    }

    private ProblemDetail handleException(ErrorCode errorCode,
                                          Exception exception,
                                          String... messageParameters) {

        String userMessage;
        if (exception instanceof DomainException de && de.usesCustomMessage()) {
            userMessage = de.getMessage();
        } else {
            userMessage = resolveMessage(errorCode, messageParameters);
        }

        logException(errorCode, exception, userMessage);

        ProblemDetail pd = ProblemDetail.forStatus(errorCode.statusCode);
        pd.setType(createErrorUri(errorCode));
        pd.setDetail(userMessage);
        pd.setTitle(errorCode.name());
        pd.setProperty("timestamp", now());
        return pd;
    }

    private String resolveMessage(ErrorCode errorCode, String... messageParameters) {
        return messageSource.getMessage(
                ERROR_PREFIX + errorCode,
                messageParameters,
                "Internal Error",
                getLocale()
        );
    }

    private void logException(ErrorCode errorCode, Exception exception, String userMessage) {
        if (errorCode.statusCode.is5xxServerError()) {
            log.error("Server error {}: {}. Exception: ",
                    errorCode, userMessage, exception);
        } else {
            log.warn("Client error {}: {}", errorCode, userMessage);
            log.debug("Stack trace:", exception);
        }
    }

    private URI createErrorUri(ErrorCode errorCode) {
        return URI.create(errorTypeBase + errorCode.name()
                                                   .toLowerCase()
                                                   .replace('_', '-'));
    }


}