package com.dantn.weblaptop.dto.request.create_request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddToGioHangRequest {
    String sessionId;
    Long idKhachHang;
    Long idSPCT;
    Integer soLuong;
//    BigDecimal gia;
}
