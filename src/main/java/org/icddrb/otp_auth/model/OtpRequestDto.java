package org.icddrb.otp_auth.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class OtpRequestDto {

    @NotNull(message = "Platform can not be null")
    @NotEmpty(message = "Platform can not be empty")
    private String platform;
    @NotNull(message = "Device ID can not be null")
    @NotEmpty(message = "Device ID can not be empty")
    private String deviceId;

    private String mobile;

    private String email;

    private String otp;


}
