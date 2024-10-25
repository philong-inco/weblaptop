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
    Integer thanhToanSau;//0 ttl 1 tt
    Long phuongThucThanhToan;// 1 tiền mặt ; 2 ck
    String ghiChu;
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
    String diaChi;
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
