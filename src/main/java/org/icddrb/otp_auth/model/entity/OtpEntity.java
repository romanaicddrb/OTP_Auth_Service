package org.icddrb.otp_auth.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.icddrb.otp_auth.model.enums.OTPStatus;
import org.icddrb.otp_auth.model.enums.Status;

@Data
@NoArgsConstructor
@Entity
@Table(name = SchemaNames.OTP_TABLE_NAME)
public class OtpEntity extends BaseEntity {

    private String platform; // Web / Android / iOS
    private String deviceId;

    @Column(nullable = false, updatable = false)
    private String otp;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OTPStatus otpStatus = OTPStatus.UnUsed;  // Used / UnUsed

    private String mobile;
    private String smsMessage;
    @Enumerated(EnumType.STRING)
    private Status smsStatus = Status.NotSend;

    private String smsFailReason;

    private String email;
    private String emailMessage;
    @Enumerated(EnumType.STRING)
    private Status emailStatus = Status.NotSend;

    private String emailFailReason;
}
