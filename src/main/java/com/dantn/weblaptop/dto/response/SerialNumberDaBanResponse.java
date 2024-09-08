package com.dantn.weblaptop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SerialNumberDaBanResponse {

    Long id;
    Long hoaDonId;
    Long serialNumberId;
    Long idSPCT;
    String maSerialNumber;
    String maSPCT;
    String tenSanPham;
    BigDecimal gia;
    Integer soLuong;
//    String anh;


    // sau theem cac trường cần thiết


}
