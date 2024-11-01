package com.dantn.weblaptop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HoaDonHinhThucThanhToanResponse {

    Long id;
    BigDecimal soTien;
    BigDecimal tienNhan;
    Integer loaiThanhToan;
    String ngayTao;
    String ngaySua;
    Long phuongThanhToan;
    String nguoiTao;
    String maGiaoDich;
    String nguoiXacNhan;
}
