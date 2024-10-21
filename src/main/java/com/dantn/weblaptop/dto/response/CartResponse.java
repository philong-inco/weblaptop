package com.dantn.weblaptop.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    String sessionId;
    Long idKhachHang;
//    Long idSanPham;
//    Integer soLuong;
//    BigDecimal gia;
}
