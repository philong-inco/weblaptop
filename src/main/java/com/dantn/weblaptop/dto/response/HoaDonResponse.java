package com.dantn.weblaptop.dto.response;

import com.dantn.weblaptop.constant.HoaDonStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HoaDonResponse {

    Long id;

    Long idNhanVien;

    Long idKhachHang;

    String tenKhachHang;

    String ma;

    Integer tongSanPham;

//    BigDecimal tongTien;

    Integer loaiHoaDon;

    BigDecimal tongTienBanDau;

    BigDecimal tongTienPhaiTra;

    String ngayMongMuonNhanHang;

    String sdt;

    String email;

    String diaChi;

    String ghiChu;

    HoaDonStatus trangThai;

    String nguoiTao;

    String nguoiSua;

    String ngayTao;
// PGG
    BigDecimal giaTriPhieuGiamGia;
    Integer loaiPGG;
    String maPGG;



}
