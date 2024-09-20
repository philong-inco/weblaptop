package com.dantn.weblaptop.dto.request.update_request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateGotGiamGiaRequest {
    String ma;
    @NotBlank(message = "Tên Đợt Giảm Giá Trống !")
    @Size(min = 1, max = 255, message = "Tên Vượt Quá 255 ký tự !")
    String ten;
    Integer trangThai;
    @NotBlank(message = "Mô Tả Đợt Giảm Giá Trống !")
    @Size(min = 1, max = 255, message = "Mô Tả Vượt Quá 255 ký tự !")
    String moTa;
    @NotBlank(message = "Loại Chiết Khấu không được để trống!")
    Integer loaiChietKhau;
    @NotNull(message = "=START_DATE_NOT_NULL")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime thoiGianBatDau;
    @NotNull(message = "END_DATE_NOT_NULL")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime thoiGianKetThuc;
    @NotNull(message = "COUPONS_DISCOUNT_VALUE_NOT_NULL")
    @Min(value = 1, message = "COUPONS_MIN_DISCOUNT_VALUE")
    BigDecimal giaTriGiam;
    List<Long> listSanPhamChiTiet;
}
