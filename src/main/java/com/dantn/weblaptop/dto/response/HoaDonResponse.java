package com.dantn.weblaptop.dto.response;

import com.dantn.weblaptop.constant.HoaDonStatus;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HoaDonResponse {

    Long id;
    Long idNhanVien;
    String tenNhanVien;
    String maNhanVien;
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
    String tinh;
    String huyen;
    String phuong;

    BigDecimal tienShip;
    BigDecimal tienGiamHangKhachHang;
    Integer thanhToanSau; // 0 tt luôn : 1 là sau
    LocalDateTime ngayGiaoHang;
    LocalDateTime ngayNhanHang;
    LocalDateTime ngayThanhToan;

    String ghiChu;

    HoaDonStatus trangThai;

    String nguoiTao;

    String nguoiSua;

    String ngayTao;
// PGG
    Long idPhieuGiamGia;
    BigDecimal giaTriPhieuGiamGia;
    Integer loaiPGG;
    String maPGG;
}
