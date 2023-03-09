package org.icddrb.otp_auth.service.impl;

import org.icddrb.otp_auth.model.EmailInfo;
import org.icddrb.otp_auth.model.OtpRequestDto;
import org.icddrb.otp_auth.model.SmsResponse;
import org.icddrb.otp_auth.model.entity.OtpEntity;
import org.icddrb.otp_auth.model.enums.OTPStatus;
import org.icddrb.otp_auth.model.enums.Status;
import org.icddrb.otp_auth.repository.OtpRepository;
import org.icddrb.otp_auth.service.EmailService;
import org.icddrb.otp_auth.service.OtpService;
import org.icddrb.otp_auth.service.SMSService;
import org.icddrb.otp_auth.exception.BaseException;
import org.icddrb.otp_auth.utils.BeanValidator;
import org.icddrb.otp_auth.utils.OTPGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpServiceImpl implements OtpService {
    private int projectId = 1;

    private final OtpRepository otpRepository;
    private final SMSService smsService;
    private final EmailService emailService;
    private final Logger logger = LoggerFactory.getLogger(OtpServiceImpl.class);

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

        entity.setOtp(OTPGenerator.generateOTP(4));

        if ((entity.getEmail() != null) &&
                !entity.getEmail().isEmpty() &&
                !entity.getEmail().isBlank()){
            entity.setEmailMessage("Hello, Your code is " + entity.getOtp() + " (Don't share with others)");
            entity.setEmailStatus(Status.Pending);
        }

        if ((entity.getMobile() != null) &&
                !entity.getMobile().isEmpty() &&
                !entity.getMobile().isBlank()){
            entity.setSmsMessage("<#> Your code is " + entity.getOtp() + " sXzcZUledx5");
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
