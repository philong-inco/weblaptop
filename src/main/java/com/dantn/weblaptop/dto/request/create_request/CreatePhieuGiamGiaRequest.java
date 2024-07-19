package com.dantn.weblaptop.dto.request.create_request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class CreatePhieuGiamGiaRequest {

    String ma;
//    Integer trangThai;
    String ten;
    String moTa;
//    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate ngayBatDau;
//    @JsonFormat(pattern = "dd-MM-yyyy")
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
