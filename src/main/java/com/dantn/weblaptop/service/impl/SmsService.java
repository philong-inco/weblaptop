package com.dantn.weblaptop.service.impl;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    private final VonageClient vonageClient;

    @Value("${vonage.api.sender}")
    private String sender;

    public SmsService(@Value("${vonage.api.key}") String apiKey,
                      @Value("${vonage.api.secret}") String apiSecret) {
        this.vonageClient = VonageClient.builder()
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .build();
    }

    public boolean sendSms(String phoneNumber, String message) {
        String sender = "DEFAULT";
        try {
            SmsSubmissionResponse response = vonageClient.getSmsClient().submitMessage(new TextMessage(
                    sender,
                    phoneNumber,
                    message
            ));

            if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
                System.out.println("Message sent successfully.");
                return true;
            } else {
                System.out.println("Message failed with error: " + response.getMessages().get(0).getErrorText());
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error while sending SMS: " + e.getMessage());
            return false;
        }
    }

}
