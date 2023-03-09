package org.icddrb.otp_auth.controller;

import org.icddrb.otp_auth.model.OtpRequestDto;
import org.icddrb.otp_auth.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/otp", produces = MediaType.APPLICATION_JSON_VALUE)
public class OtpController {
    private final OtpService otpService;

    @Autowired
    public OtpController(OtpService otpService){
        this.otpService = otpService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendOTP(@RequestBody OtpRequestDto object) {
        return new ResponseEntity<>(otpService.send(object), HttpStatus.OK);
    }

    @PostMapping(value = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> otpVerify(@RequestBody OtpRequestDto object) {
        return new ResponseEntity<>(otpService.verify(object), HttpStatus.OK);
    }

}
