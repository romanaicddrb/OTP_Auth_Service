package org.icddrb.otp_auth.repository;

import org.icddrb.otp_auth.model.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OtpRepository extends JpaRepository<OtpEntity, Long> {

    OtpEntity findByOtp(String otp);

    OtpEntity findByPlatformAndDeviceIdAndMobileAndEmailAndOtp(String platform,
                                                               String deviceID,
                                                               String mobile,
                                                               String email,
                                                               String otp);

}
