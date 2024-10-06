package org.romana.otp_auth_service.model;

import javax.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OtpRequestDto {
    private int projectId = 1;

    @NotNull(message = "Platform can not be null")
    @NotEmpty(message = "Platform can not be empty")
    private String platform;
    @NotNull(message = "Device ID can not be null")
    @NotEmpty(message = "Device ID can not be empty")
    private String deviceId;

    private String mobile;

    private String email;

    private String purpose;

    private String otp;



}
