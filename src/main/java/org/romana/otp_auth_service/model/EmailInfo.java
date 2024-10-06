package org.romana.otp_auth_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class EmailInfo {

    @NotNull(message = "Email address can not be null")
    @NotEmpty(message = "Email address can not be empty")
    private String emailTo;

    @NotNull(message = "Email subject can not be null")
    @NotEmpty(message = "Email subject can not be empty")
    private String subject;

    @NotNull(message = "Email message can not be null")
    @NotEmpty(message = "Email message can not be empty")
    private String message;
}