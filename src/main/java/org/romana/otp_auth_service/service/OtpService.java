package org.romana.otp_auth_service.service;

import org.romana.otp_auth_service.model.OtpRequestDto;

public interface OtpService {
    String send(OtpRequestDto object);

    String verify(OtpRequestDto object);

}
