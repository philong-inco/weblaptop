package com.dantn.weblaptop.dto.response.pdf;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HoaDonHinhThucThanhToanPdfReponse {

    String maGioDich;
    String soTien;
    String tienNhan;
    Integer loaiThanhToan; // 0 đã tt : 1 trả sau
    //
    Integer trangThai;// 0 thành công 1 : chờ:
}
