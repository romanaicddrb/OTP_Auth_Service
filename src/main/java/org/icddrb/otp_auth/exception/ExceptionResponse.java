package org.icddrb.otp_auth.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {

    String timestamp;
    int status;
    String error;
    String message;

}
