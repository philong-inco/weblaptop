package com.dantn.weblaptop.dotgiamgia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DotGiamGiaDTO {
    private Long id;
    String ma;
    Integer trangThai;
    String ten;
    String moTa;
    Integer loaiChietKhau;
    LocalDateTime thoiGianBatDau;
    LocalDateTime thoiGianKetthuc;
    BigDecimal giamToiDa;

}
