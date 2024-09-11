package com.dantn.weblaptop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SerialNumberDaBanResponse {

    Long billId;
    Long productDetailId;
    String productDetailCode;
    String productName;
    BigDecimal gia;
    Integer soLuong;
    Set<SerialInfo> serialNumbers;

//    String anh;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class SerialInfo {
        Long serialNumberId;
        String serialNumberCode;
    }
}
