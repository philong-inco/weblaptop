package com.dantn.weblaptop.dotgiamgia.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DotGiamGiaResponse {
    Long id;
    String ma;
    String ten;
    Integer trangThai;
    String moTa;
    Integer loaiChietKhau;
    LocalDateTime thoiGianBatDau;
    LocalDateTime thoiGianKetthuc;
    BigDecimal giamToiDa;
}
