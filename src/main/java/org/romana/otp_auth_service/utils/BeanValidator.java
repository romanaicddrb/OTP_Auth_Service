package org.romana.otp_auth_service.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;

public class BeanValidator<T> {
    private final Validator validator;

    public BeanValidator(){
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public String checkValidation(T obj){

        Set<ConstraintViolation<T>> violations = validator.validate(obj);

        for (ConstraintViolation<T> violation : violations) {
            return violation.getMessage();
        }
        return null;
    }
}