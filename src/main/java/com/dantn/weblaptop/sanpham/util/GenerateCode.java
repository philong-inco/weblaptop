package com.dantn.weblaptop.sanpham.util;

import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class GenerateCode {
    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final String RAM_PREFIX = "RAM";
    public static final String VGA_PREFIX = "GVA";
    public static final String CPU_PREFIX = "CPU";
    public static final String MAN_HINH_PREFIX = "SCR";
    public static final String O_CUNG_PREFIX = "DIS";
    public static final String HE_DIEU_HANH_PREFIX = "SYS";
    public static final String WEBCAM_PREFIX = "WEC";
    public static final String MAU_SAC_PREFIX = "COL";
    public static final String BAN_PHIM_PREFIX = "KEB";
    public static final String SAN_PHAM_PREFIX = "PRO";
    public static final String SAN_PHAM_CHI_TIET_PREFIX = "PDE";
    public static final String THUONG_HIEU_PREFIX = "BRA";
    public static final String NHU_CAU_PREFIX = "DMA";
    public static final Random random = new Random();
    public static final int SUFFIXES_LENGTH = 5;
    public static String generateCode(String prefix){
        StringBuilder result = new StringBuilder();
        result.append(prefix);
        for (int i = 0; i < SUFFIXES_LENGTH; i++) {
            result.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return result.toString();
    }

//
}
