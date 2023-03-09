package org.icddrb.otp_auth.service;

import org.icddrb.otp_auth.model.OtpRequestDto;

public interface OtpService {
    String send(OtpRequestDto object);

    String verify(OtpRequestDto object);

}
