package org.icddrb.otp_auth.service;

import org.icddrb.otp_auth.model.SmsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="bulksmsbd", url="https://bulksmsbd.net/api/")
public interface SMSService {

    @GetMapping(value = "smsapi", produces = "application/json")
    SmsResponse smsSendRequest(@RequestParam("api_key") String apiKey,
                               @RequestParam("type") String type,
                               @RequestParam("senderid") String sender,
                               @RequestParam("number") String number,
                               @RequestParam("message") String msg);

    default SmsResponse sendSMS(String number, String msg){
        return smsSendRequest("7fQ5AR7aXejtJ3rWAI7W",
                "text",
                "8809601004411",
                number,msg);
    }

}
