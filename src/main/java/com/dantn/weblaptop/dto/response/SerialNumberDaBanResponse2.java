package com.dantn.weblaptop.dto.response;

import java.math.BigDecimal;

public interface SerialNumberDaBanResponse2 {
    Long id();
    Long hoaDonId();
    Long serialNumberId();
    Long idSPCT();
    String  maSerialNumber();
    String maSPCT();
    String tenSanPham();
    BigDecimal gia();
    Integer soLuong();
}
