package org.romana.otp_auth_service.service.impl;

import org.romana.otp_auth_service.model.EmailInfo;
import org.romana.otp_auth_service.model.OtpRequestDto;
import org.romana.otp_auth_service.model.SmsResponse;
import org.romana.otp_auth_service.model.entity.OtpEntity;
import org.romana.otp_auth_service.model.enums.OTPStatus;
import org.romana.otp_auth_service.model.enums.Status;
import org.romana.otp_auth_service.repository.OtpRepository;
import org.romana.otp_auth_service.service.EmailService;
import org.romana.otp_auth_service.service.OtpService;
import org.romana.otp_auth_service.service.SMSService;
import org.romana.otp_auth_service.exception.BaseException;
import org.romana.otp_auth_service.utils.BeanValidator;
import org.romana.otp_auth_service.utils.OTPGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class OtpServiceImpl implements OtpService {
    private int projectId = 1;

    private final OtpRepository otpRepository;
    private final SMSService smsService;
    private final EmailService emailService;
    private final Logger logger = LoggerFactory.getLogger(OtpServiceImpl.class);

    @Value("${spring.otp.life}")
    private int otpTime;

    @Value("${spring.otp.length}")
    private int otpLength;

    @Value("${spring.otp.mail.msg}")
    private String mailMsg;

    @Value("${spring.otp.sms.msg}")
    private String smsMsg;


    @Autowired
    public OtpServiceImpl(OtpRepository otpRepository,
                          SMSService smsService,
                          EmailService emailService
    ) {
        this.otpRepository = otpRepository;
        this.smsService = smsService;
        this.emailService = emailService;
    }

    @Override
    public String send(OtpRequestDto object) {
        try {
            logger.info("otp send event start");
            String checkValidation = new BeanValidator<OtpRequestDto>().checkValidation(object);
            if (checkValidation != null) {
                throw new BaseException(checkValidation);
            }
            OtpEntity entity =  convertToEntity(object);

            if (entity.getSmsStatus().equals(Status.Pending)) {
                sendSms(entity);
            }

            if (entity.getEmailStatus().equals(Status.Pending)) {
                sendEmail(entity);
            }

            otpRepository.save(entity);

            return "OTP send successfully";
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e.getMessage());
        }
    }

    @Override
    public String verify(OtpRequestDto object) {
        try {
            logger.info("otp verification process start");
            String checkValidation = new BeanValidator<OtpRequestDto>().checkValidation(object);
            if (checkValidation != null) {
                throw new BaseException(checkValidation);
            }

            if(object.getOtp().isEmpty()){
                throw new BaseException("OTP can not be empty");
            }

            OtpEntity entity = otpRepository.findByPlatformAndDeviceIdAndMobileAndEmailAndOtp(object.getPlatform(),
                    object.getDeviceId(), object.getMobile(), object.getEmail(), object.getOtp());

            if((entity == null) ||
                    (!entity.getEmailStatus().equals(Status.Sent) &&
                            !entity.getSmsStatus().equals(Status.Sent))){
                throw new BaseException("Invalid OTP");
            }


            if(entity.getOtpStatus().equals(OTPStatus.Used)){
                throw new BaseException("OTP is already used");
            }

            Duration duration = Duration.between(entity.getCreateDateTime(), LocalDateTime.now());
            if (duration.toMinutes() > otpTime){
                throw new BaseException("OTP is expired");
            }

            entity.setOtpStatus(OTPStatus.Used);
            entity.setUpdatedBy("Service");

            otpRepository.save(entity);

            return "OTP is valid";
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e.getMessage());
        }
    }

    OtpEntity convertToEntity(OtpRequestDto object){

        OtpEntity entity = new OtpEntity();
        entity.setProjectId(projectId);
        entity.setPlatform(object.getPlatform());
        entity.setDeviceId(object.getDeviceId());
        entity.setMobile(object.getMobile());
        entity.setEmail(object.getEmail());

        entity.setOtp(OTPGenerator.generateOTP(otpLength));

        if ((entity.getEmail() != null) &&
                !entity.getEmail().isEmpty() &&
                !entity.getEmail().isBlank()){
            entity.setEmailMessage(mailMsg.replace("#OTP#", entity.getOtp()));
            entity.setEmailStatus(Status.Pending);
        }

        if ((entity.getMobile() != null) &&
                !entity.getMobile().isEmpty() &&
                !entity.getMobile().isBlank()){
            entity.setSmsMessage(smsMsg.replace("#OTP#", entity.getOtp()));
            entity.setSmsStatus(Status.Pending);
        }

        entity.setCreatedBy("Service");

        return entity;

    }

    void sendEmail(OtpEntity entity){

        logger.info("email sending process start");

        try{
            EmailInfo emailInfo = new EmailInfo();
            emailInfo.setEmailTo(entity.getEmail());
            emailInfo.setSubject("OTP");
            emailInfo.setMessage(entity.getEmailMessage());

            emailService.sendTextEmail(emailInfo);

            entity.setEmailStatus(Status.Sent);

            logger.info("email sending process done");

        }catch (Exception e){
            e.printStackTrace();
            entity.setEmailStatus(Status.Fail);
            entity.setEmailFailReason(e.getMessage());
        }
    }

    void sendSms(OtpEntity entity){

        logger.info("sms sending process start");

        try {
            SmsResponse response = smsService.sendSMS(entity.getMobile(), entity.getSmsMessage());

            if (response.getResponse_code() == 202) {
                entity.setSmsStatus(Status.Sent);
            } else {
                entity.setSmsStatus(Status.Fail);
                entity.setSmsFailReason(response.getError_message());
            }

            logger.info("sms sending process done. response code : " + response.getResponse_code());

        }catch (Exception e){
            e.printStackTrace();
            entity.setSmsStatus(Status.Fail);
            entity.setSmsFailReason(e.getMessage());
        }

    }
}
