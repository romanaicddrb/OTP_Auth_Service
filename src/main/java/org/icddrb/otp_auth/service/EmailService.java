package org.icddrb.otp_auth.service;

import org.icddrb.otp_auth.model.EmailInfo;

public interface EmailService {
    void sendTextEmail(EmailInfo emailInfo);
    void sendHtmlEmail(EmailInfo emailInfo);
}
