package org.romana.otp_auth_service.service.impl;

import org.romana.otp_auth_service.model.SmsResponse;
import org.romana.otp_auth_service.service.SMSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
public class SMSServiceImpl implements SMSService {

    private final static Logger logger = LoggerFactory.getLogger(SMSServiceImpl.class);

    private final WebClient webClient;

    @Value("${spring.sms.api_key}")
    private String apiKey;

    @Value("${spring.sms.sender_id}")
    private String sender;


    @Autowired
    public SMSServiceImpl() {
        this.webClient = WebClient.create();
    }
    @Override
    public SmsResponse sendSMS(String number, String msg) {

        logger.info("sendSMS called");

        String apiUrl = "https://bulksmsbd.net/api/smsapi";

        String urlWithParam = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("api_key", apiKey)
                .queryParam("type", "text")
                .queryParam("senderid", sender)
                .queryParam("number", number)
                .queryParam("message", msg)
                .build(false)
                .toUriString();

        logger.info("urlWithParam " + urlWithParam);

        Mono<SmsResponse> monoResponse = webClient
                .get()
                .uri(urlWithParam)
                .retrieve()
                .bodyToMono(SmsResponse.class);

        return monoResponse.block();

    }

}
