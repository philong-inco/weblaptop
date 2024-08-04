package com.dantn.weblaptop.dto.response;

import com.dantn.weblaptop.entity.khachhang.KhachHang;
import com.dantn.weblaptop.entity.phieugiamgia.KhachHangPhieuGiamGia;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhieuGiamGiaResponse2 {

    Long id;

    String ma;

    Integer trangThai;

    String ten;

    String moTa;

    LocalDate ngayBatDau;

    LocalDate ngayHetHan;

    Integer loaiGiamGia;

    BigDecimal giaTriGiamGia;

    BigDecimal giaTriDonToiThieu;

    BigDecimal giamToiGia;

    Integer phamViApDung;

    String ngayTao;

    String ngaySua;

    String nguoiTao;

    String nguoiSua;

    Integer soLuong;

    Set<Long> khachHangPhieuGiamGias;
}
