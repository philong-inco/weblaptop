package com.dantn.weblaptop.dto.request.create_request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateHoaDonClientRequest {
    // info
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
    @NotNull(message = "PAY_LATER_NOT_NULL")
    Integer thanhToanSau;//0 ttl 1 tt
    @NotNull(message = "PAYMENT_METHOD_NOT_NULL")
    Long phuongThucThanhToan;// 1 tiền mặt ; 2 ck
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
    String ghiChu;
    // amount
    @NotNull(message = "TONG_TIEN_BAN_DAU_NOT_NULL")
    @Min(value = 0, message = "TONG_TIEN_PHAI_TRA_KHONG_AM")
    BigDecimal tongTienBanDau;
    @NotNull(message = "TONG_TIEN_PHAI_TRA_NOT_NULL")
    @Min(value = 0, message = "TONG_TIEN_PHAI_TRA_KHONG_AM")
    BigDecimal tongTienPhaiTra;
    @NotNull(message = "MONEY_SHIP")
    BigDecimal tienShip;
    // pgg
    String maPGG;
    BigDecimal giaTriPGG;
    @NotNull(message = "SHIPPING_DATE_NOT_NULL")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    LocalDate ngayNhanHangDuKien;
    String maGiaDich;
    @Valid
    List<GioHangChiTietRequest> gioHangChiTiet;
}
