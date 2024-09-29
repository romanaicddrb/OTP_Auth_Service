package org.romana.otp_auth_service.utils;

import java.util.Random;

public class OTPGenerator {
    public static String generateOTP(int otpLength) {
        Random random=new Random();

        StringBuilder sb=new StringBuilder();

        for(int i=0 ; i< otpLength ; i++)
        {
            sb.append(random.nextInt(10));
        }
        String otp=sb.toString();

        return otp;
    }
}
