package org.romana.otp_auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class OtpAuthenticationApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(OtpAuthenticationApplication.class, args);
	}



}
