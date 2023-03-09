package org.icddrb.otp_auth.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

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