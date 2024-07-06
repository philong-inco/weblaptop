package com.dantn.weblaptop.constant;

import java.util.Random;

public class GenerateCode {
    private static final String NhanVien = "Empl0";
    private static final String KhachHang = "Cus0";
    private static final String VaiTro = "Role0";
    private static final int NUMBER_LENGTH = 4;

    public static String generateNhanVienCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return NhanVien + formattedNumber;
    }
    public static String generateKhachHangCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return KhachHang + formattedNumber;
    }
    public static String generateVaiTroCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return VaiTro + formattedNumber;
    }
}
