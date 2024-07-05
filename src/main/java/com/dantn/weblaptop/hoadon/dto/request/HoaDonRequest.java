package com.dantn.weblaptop.hoadon.dto.request;

import com.dantn.weblaptop.constant.HoaDonStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HoaDonRequest {

    Long id;

    @JsonProperty("nhan_vien_id")
    Long idNhanVien;

    @JsonProperty("khach_hang_id")
    Long idKhachHang;

    @JsonProperty("ma_phieu_giam_gia")
    String maPhieuGiamGia;

//    String ma;

    @JsonProperty("loai_hoa_don")
    Integer loaiHoaDon;

    @JsonProperty("tong_tien_ban_dau")
    BigDecimal tongTienBanDau;

    @JsonProperty("tong_tien_phai_tra")
    BigDecimal tongTienPhaiTra;

    @JsonProperty("ngay_mong_muon_nhan_hang")
    LocalDate ngayMongMuonNhanHang;

    @JsonProperty("sdt")
    String sdt;

    @JsonProperty("email")
    String email;

    @JsonProperty("tinh_thanh")
    String tinhThanh;

    @JsonProperty("quan_huyen")
    String quanHuyen;

    @JsonProperty("xa_phuong")
    String xaPhuong;

    @JsonProperty("dia_chi")
    String diaChi;

    @JsonProperty("ghi_chu")
    String ghiChu;

    @JsonProperty("trang_thai")
    @Enumerated(EnumType.STRING)
    HoaDonStatus trangThai;

    //
    @JsonProperty("ghi_chu_cho_khach_hang")
    String ghiChuChoKhachHang;

    @JsonProperty("ghi_chu_cho_cua_hang")
    String ghiChuChoCuaHang;
}
