package org.romana.otp_auth_service.service;

import org.romana.otp_auth_service.model.SmsResponse;

public interface SMSService {
    SmsResponse sendSMS(String number, String msg);
}
