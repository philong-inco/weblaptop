package com.dantn.weblaptop.dto.request.create_request;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePhieuGiamGiaRequest {

    String ma;
//    Integer trangThai;
    String ten;
    String moTa;
    LocalDateTime ngayBatDau;
    LocalDateTime ngayHetHan;
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
