package org.romana.otp_auth_service.repository;

import org.romana.otp_auth_service.model.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OtpRepository extends JpaRepository<OtpEntity, Long> {

    OtpEntity findByOtp(String otp);

    OtpEntity findByPlatformAndDeviceIdAndMobileAndEmailAndOtp(String platform,
                                                               String deviceID,
                                                               String mobile,
                                                               String email,
                                                               String otp);

}
