package com.dantn.weblaptop.dto.request.update_request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePhieuGiamGiaRequest {

    String ma;
//    Integer trangThai;
    String ten;
    String moTa;
    LocalDate ngayBatDau;
    LocalDate ngayHetHan;
    Integer loaiGiamGia;
    BigDecimal giaTriGiamGia;
    BigDecimal giaTriDonToiThieu;
    BigDecimal giamToiGia;
    Integer phamViApDung;
    Integer soLuong;
    Long ngayTao;
    Long ngaySua;
    String nguoiTao;
    String nguoiSua;

    List<Long> listKhachHang;
}
