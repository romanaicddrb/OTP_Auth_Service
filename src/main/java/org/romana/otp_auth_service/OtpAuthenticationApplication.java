package org.romana.otp_auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//@EnableFeignClients
public class OtpAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtpAuthenticationApplication.class, args);
	}



}
