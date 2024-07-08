package com.dantn.weblaptop.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ConvertTime {

    public static String convert(String time) {
        Instant instant = Instant.ofEpochMilli(Long.valueOf(time));
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return localDateTime.format(formatter);
    }

    public static long convertStringToLong(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(time, formatter);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date.getTime();
    }

    public static LocalDateTime convertStringToLocalDateTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        return localDateTime;
    }

    public static void main(String[] args) {
        // Test
        System.out.println(convert("1716310800000"));
//        System.out.println(convertStringToLong("22-05-2024"));
    }

}
