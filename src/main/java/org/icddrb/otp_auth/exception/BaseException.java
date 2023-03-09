package org.icddrb.otp_auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BaseException extends RuntimeException{
    public BaseException(String message) {
        super(message);
    }

}
