package com.dantn.weblaptop.controller;

import com.dantn.weblaptop.service.impl.OtpService;
import com.dantn.weblaptop.service.impl.SmsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sms-otp")
@AllArgsConstructor
@Component
@CrossOrigin(origins = "*")
public class OtpController {
    @Autowired
    private OtpService otpService;
    @Autowired
    private SmsService smsService;

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String phoneNumber, @RequestParam String action) {
        // Kiểm tra trường hợp sử dụng (đăng ký, đăng nhập, quên mật khẩu, xác minh thanh toán, v.v.)
        switch (action.toLowerCase()) {
            case "register":
            case "login":
            case "forgot_password":
            case "payment_verification":
            case "update_profile":
                String otp = otpService.generateOtp(phoneNumber);
                String message = "Your " + action.replace("_", " ") + " OTP is: " + otp;
                boolean isSent = smsService.sendSms(phoneNumber, message);
                return isSent ? "OTP sent successfully for " + action.replace("_", " ") : "Failed to send OTP for " + action.replace("_", " ");
            default:
                return "Invalid request action. Must be one of 'register', 'login', 'forgot_password', 'payment_verification', 'update_profile'.";
        }
    }

    // API chung để xác thực OTP cho nhiều trường hợp
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String phoneNumber, @RequestParam String otp, @RequestParam String action) {
        // Kiểm tra trường hợp sử dụng (đăng ký, đăng nhập, quên mật khẩu, xác minh thanh toán, v.v.)
        switch (action.toLowerCase()) {
            case "register":
            case "login":
            case "forgot_password":
            case "payment_verification":
            case "update_profile":
                boolean isValid = otpService.validateOtp(phoneNumber, otp);
                return isValid ? "OTP verified, " + action.replace("_", " ") + " successful" : "Invalid or expired OTP";
            default:
                return "Invalid request action. Must be one of 'register', 'login', 'forgot_password', 'payment_verification', 'update_profile'.";
        }
    }
}
