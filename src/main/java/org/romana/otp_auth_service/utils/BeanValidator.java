package org.romana.otp_auth_service.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
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