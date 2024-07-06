package com.dantn.weblaptop.hoadon.dto.response;

import com.dantn.weblaptop.constant.HoaDonStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @JsonProperty("nhan_vien_id")
    Long idNhanVien;

    @JsonProperty("khach_hang_id")
    Long idKhachHang;

    @JsonProperty("gia_tri_phieu_giam_gia")
    BigDecimal giaTriPhieuGiamGia;

    String ma;

    @JsonProperty("loai_hoa_don")
    @Min(value = 0, message = "Loại hóa đơn không hợp lệ")
    Integer loaiHoaDon;

    @JsonProperty("tong_tien_ban_dau")
    @Min(value = 0, message = "Tổng tiển ban đầu")
    private BigDecimal tongTienBanDau;

    @JsonProperty("tong_tien_phai_tra")
    private BigDecimal tongTienPhaiTra;

    @JsonProperty("ngay_mong_muon_nhan_hang")
    private LocalDate ngayMongMuonNhanHang;

    @JsonProperty("sdt")
    @Size(min = 0, max = 10, message = "Số điện thoại phải đủ 10 chữ số")
    private String sdt;

    @JsonProperty("email")
    @NotBlank(message = "Email không được để trống")
    @Size(min = 0, message = "Email ")
    @Email(message = "Email sai định dạng")
    private String email;

    @JsonProperty("tinh_thanh")
    String tinhThanh;

    @JsonProperty("quan_huyen")
    String quanHuyen;

    @JsonProperty("xa_phuong")
    String xaPhuong;

    @JsonProperty("dia_chi")
    private String diaChi;

    @JsonProperty("ghi_chu")
    private String ghiChu;

    @JsonProperty("trang_thai")
    @Enumerated(EnumType.STRING)
    HoaDonStatus trangThai;

    // tao , sua
}
