package com.dantn.weblaptop.dto.response.pdf;

import com.dantn.weblaptop.constant.HoaDonStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BillPdfResponse {
    Long id;
    Long idNhanVien;
    Long idKhachHang;
    String tenKhachHang;
    String ma;
    Integer tongSanPham;
    //    BigDecimal tongTien;
    Integer loaiHoaDon;
    String tongTienBanDau;
    String tongTienPhaiTra;
    String ngayMongMuonNhanHang;
    String sdt;
    String email;
    String diaChi;
    String tinh;
    String huyen;
    String phuong;

    String tienShip;
    String tienGiamHangKhachHang;
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
    String giaTriPhieuGiamGia;
    Integer loaiPGG;
    String maPGG;
    String qrCode;
}
