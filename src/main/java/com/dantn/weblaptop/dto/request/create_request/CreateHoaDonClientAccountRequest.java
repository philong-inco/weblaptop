package com.dantn.weblaptop.dto.request.create_request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateHoaDonClientAccountRequest {
    // info
    @NotNull(message = "CUSTOMER_ID_IS_NULL")
    Long idKhacHang;
    @NotBlank(message = "NAME_NOT_BLANK")
    @Size(min = 5, max = 25, message = "NAME_MIN_MAXIMUM")
    String tenKhachHang;
    @NotBlank(message = "PHONE_NOT_BLANK")
    @Pattern(regexp = "^0\\d{9}$", message = "PHONE_INVALID")
    String sdt;
    @NotBlank(message = "EMAIL_NOT_BLANK")
    @Email(message = "EMAIL_INVALID")
    String email;
    Integer thanhToanSau;//0 ttl 1 tt
    Long phuongThucThanhToan;// 1 tiền mặt ; 2 ck
    String ghiChu;
    // address
    @NotBlank(message = "PROVINCIAL_CITY_NOT_BLANK")
    String idTinhThanh;
    @NotBlank(message = "PROVINCIAL_CITY_NAME_NOT_BLANK")
    String tenTinhThanh;
    @NotBlank(message = "DISTRICT_NOT_BLANK")
    String idQuanHuyen;
    @NotBlank(message = "DISTRICT_NAME_NOT_BLANK")
    String tenQuanHuyen;
    @NotBlank(message = "WARD_NOT_BLANK")
    String idPhuongXa;
    @NotBlank(message = "WARD_NAME_NOT_BLANK")
    String tenPhuongXa;
    String diaChi;
    // amount
    @NotNull(message = "TONG_TIEN_BAN_DAU_NOT_NULL")
    BigDecimal tongTienBanDau;
    @NotNull(message = "TONG_TIEN_PHAI_TRA_NOT_NULL")
    BigDecimal tongTienPhaiTra;
    BigDecimal giamHangKhachHang;
    @NotNull(message = "MONEY_SHIP")
    BigDecimal tienShip;
    // pgg
    String maPGG;
    BigDecimal giaTriPGG;
    //    BigDecimal tienShip;
    @Valid
    List<GioHangChiTietRequest> gioHangChiTiet;
}
