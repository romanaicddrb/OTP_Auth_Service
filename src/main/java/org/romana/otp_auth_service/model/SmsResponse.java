package org.romana.otp_auth_service.model;

import lombok.Data;

@Data
public class SmsResponse {
    private int response_code;
    private int message_id;
    private String success_message;
    private String error_message;
}
