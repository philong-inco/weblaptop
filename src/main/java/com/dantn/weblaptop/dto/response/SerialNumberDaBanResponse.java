package com.dantn.weblaptop.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SerialNumberDaBanResponse {

    Long id;

    Long hoaDonId;

    Long serialNumberId;

    String tenSanPham;

    BigDecimal gia;

    String anh;
    // sau theem cac trường cần thiết
}
