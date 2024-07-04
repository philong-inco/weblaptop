package com.dantn.weblaptop.dotgiamgia.model.request;

import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGiaSanPhamChiTiet;
import com.dantn.weblaptop.entity.hoadon.SerialNumberDaBan;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateDotGiamGiaRequest {

    String ma;

    Integer trangThai;
    String ten;

    String moTa;

    Integer loaiChietKhau;

    LocalDateTime thoiGianBatDau;

    LocalDateTime thoiGianKetthuc;

    BigDecimal giamToiDa;

    Set<DotGiamGiaSanPhamChiTiet> dotGiamGiaSanPhamChiTiets;
    Set<SerialNumberDaBan> serialNumberDaBans;
}
