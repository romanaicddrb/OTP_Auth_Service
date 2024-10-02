package org.romana.otp_auth_service.controller;

import org.romana.otp_auth_service.model.OtpRequestDto;
import org.romana.otp_auth_service.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class OtpController {
    private final OtpService otpService;

    @Autowired
    public OtpController(OtpService otpService){
        this.otpService = otpService;
    }

    @GetMapping(value = "/test", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> serviceTest() {
        return new ResponseEntity<>("auth service call", HttpStatus.OK);
    }

    @PostMapping(value = "/otp", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendOTP(@RequestBody OtpRequestDto object) {
        return new ResponseEntity<>(otpService.send(object), HttpStatus.OK);
    }

    @PostMapping(value = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> otpVerify(@RequestBody OtpRequestDto object) {
        return new ResponseEntity<>(otpService.verify(object), HttpStatus.OK);
    }

}
