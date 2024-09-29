package org.romana.otp_auth_service.service;

import org.romana.otp_auth_service.model.EmailInfo;

public interface EmailService {
    void sendTextEmail(EmailInfo emailInfo);
    void sendHtmlEmail(EmailInfo emailInfo);
}
