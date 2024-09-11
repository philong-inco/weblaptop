package com.dantn.weblaptop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhieuGiamGiaResponse {

    Long id;

    String ma;

    Integer trangThai;

    String ten;

    String moTa;

    LocalDateTime ngayBatDau;

    LocalDateTime ngayHetHan;

    Integer loaiGiamGia;

    BigDecimal giaTriGiamGia;

    BigDecimal giaTriDonToiThieu;

    BigDecimal giamToiDa;

    Integer phamViApDung;

    String ngayTao;

    String ngaySua;

    String nguoiTao;

    String nguoiSua;

    Integer soLuong;

    Set<Long> khachHangPhieuGiamGias;
}
