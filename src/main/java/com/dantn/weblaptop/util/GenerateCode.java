package com.dantn.weblaptop.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GenerateCode {
    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final String SIZE_PREFIX = "SZ";
    public static final String COLOR_PREFIX = "CL";
    public static final String STAFF_PREFIX = "NV";
    public static final String CUSTOMER_PREFIX = "CS";
    public static final String PRODUCT_PREFIX = "PR";
    public static final String CATEGORY_PREFIX = "CA";
    public static final String BILL_PREFIX = "BL";
    public static final String PAYMENT_METHOD_PREFIX = "PAY_";
    public static final Random random = new Random();

    public static final int SUFFIXES_LENGTH = 5;

    public String generateCode(String prefix) {
        StringBuilder result = new StringBuilder();
        result.append(prefix);
        for (int i = 0; i < SUFFIXES_LENGTH; i++) {
            result.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return result.toString();
    }
}
