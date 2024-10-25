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
public class CreateHoaDonClientRequest {
    // info
//    Long idKhacHang;
    @NotNull(message = "SESSION_ID_IS_NULL")
    @NotBlank(message = "SESSION_ID_IS_NULL")
    String sessionId;
    @NotBlank(message = "NAME_NOT_BLANK")
    @Size(min = 5, max = 25, message = "NAME_MIN_MAXIMUM")
    String tenKhachHang;
    @NotBlank(message = "PHONE_NOT_BLANK")
    @Pattern(regexp = "^0\\d{9}$", message = "PHONE_INVALID")
    String sdt;
    @NotBlank(message = "EMAIL_NOT_BLANK")
    @Email(message = "EMAIL_INVALID")
    String email;
    String diaChi;
    //
    Integer thanhToanSau;//0 ttl 1 tt
    Long phuongThucThanhToan;// 1 tiền mặt ; 2 ck
//    Integer loaiHoaDon;

    // address
    @NotBlank(message = "PROVINCIAL_CITY_NOT_BLANK")
    String idTinhThanh;
    String tenTinhThanh;
    @NotBlank(message = "DISTRICT_NOT_BLANK")
    String idQuanHuyen;
    String tenQuanHuyen;
    @NotBlank(message = "WARD_NOT_BLANK")
    String idPhuongXa;
    String tenPhuongXa;
    String ghiChu;
    // amount
    BigDecimal tongTienBanDau;
    BigDecimal tongTienPhaiTra;
    BigDecimal giamHangKhachHang;
    BigDecimal tienShip;
    // pgg
    String maPGG;
    BigDecimal giaTriPGG;
    //    BigDecimal tienShip;
    @Valid
    List<GioHangChiTietRequest> gioHangChiTiet;
}
