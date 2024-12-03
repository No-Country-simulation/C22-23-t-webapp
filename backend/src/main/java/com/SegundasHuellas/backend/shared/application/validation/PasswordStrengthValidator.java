package com.SegundasHuellas.backend.shared.application.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordStrengthValidator implements ConstraintValidator<StrongPassword, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            addConstraintViolation(context, "Password cannot be null");
            return false;
        }

        context.disableDefaultConstraintViolation();
        boolean isValid = true;

        if (password.length() < 8) {
            addConstraintViolation(context, "Password must be at least 8 characters long");
            isValid = false;
        }

        if (!password.matches(".*\\d.*")) {
            addConstraintViolation(context, "Password must contain at least one digit");
            isValid = false;
        }

        if (!password.matches(".*[a-z].*")) {
            addConstraintViolation(context, "Password must contain at least one lowercase letter");
            isValid = false;
        }

        if (!password.matches(".*[A-Z].*")) {
            addConstraintViolation(context, "Password must contain at least one uppercase letter");
            isValid = false;
        }

        if (!password.matches(".*[@#$%^&+=!].*")) {
            addConstraintViolation(context, "Password must contain at least one special character (@#$%^&+=!)");
            isValid = false;
        }

        if (password.contains(" ")) {
            addConstraintViolation(context, "Password cannot contain whitespace");
            isValid = false;
        }

        return isValid;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message)
               .addConstraintViolation();
    }

}



