package org.romana.otp_auth_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public final ResponseEntity<ExceptionResponse> handleCustomException(BaseException baseException,
                                                                               WebRequest request) {
        int customStatusCode = 999;
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now().toString(),
                customStatusCode,
                baseException.getMessage(),
                baseException.getMessage());

        return new ResponseEntity<>(exceptionResponse, null, customStatusCode);
    }
}
