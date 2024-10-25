package com.dantn.weblaptop.service.impl;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {
    private final Map<String, OtpData> otpStorage = new HashMap<>();
    private static final int OTP_VALID_DURATION = 10 * 60 * 1000; // 5 phút

    public String generateOtp(String phoneNumber) {
        // Sinh mã OTP 6 chữ số
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        // Lưu OTP kèm theo thời gian hết hạn
        OtpData otpData = new OtpData(otp, System.currentTimeMillis() + OTP_VALID_DURATION);
        otpStorage.put(phoneNumber, otpData);

        return otp;
    }


    public boolean validateOtp(String phoneNumber, String otp) {
        OtpData otpData = otpStorage.get(phoneNumber);
        if (otpData == null) {
            return false;
        }

        if (otpData.getExpiryTime() < System.currentTimeMillis()) {
            otpStorage.remove(phoneNumber);
            return false;
        }

        if (otpData.getOtp().equals(otp)) {
            otpStorage.remove(phoneNumber);
            return true;
        }

        return false;
    }

    private static class OtpData {
        private final String otp;
        private final long expiryTime;

        public OtpData(String otp, long expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }

        public String getOtp() {
            return otp;
        }

        public long getExpiryTime() {
            return expiryTime;
        }
    }
}
