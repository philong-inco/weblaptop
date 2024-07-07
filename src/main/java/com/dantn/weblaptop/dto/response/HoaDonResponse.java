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

    BigDecimal giaTriPhieuGiamGia;

    String ma;

    Integer loaiHoaDon;

    BigDecimal tongTienBanDau;

    BigDecimal tongTienPhaiTra;

    LocalDate ngayMongMuonNhanHang;

    String sdt;

    String email;

    String tinhThanh;

    String quanHuyen;

    String xaPhuong;

    String diaChi;

    String ghiChu;

    HoaDonStatus trangThai;

    String nguoiTao;

    String nguoiSua;

}
