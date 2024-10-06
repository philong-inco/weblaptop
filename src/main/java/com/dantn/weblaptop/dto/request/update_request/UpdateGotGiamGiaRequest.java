package com.dantn.weblaptop.dto.request.update_request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Tên Đợt Giảm Giá Trống !")
    @Size(min = 1, max = 255, message = "Tên Vượt Quá 255 ký tự !")
    String ten;
    Integer trangThai;
    String moTa;
    Integer loaiChietKhau;
    @NotNull(message = "START_DATE_NOT_NULL")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime thoiGianBatDau;
    @NotNull(message = "END_DATE_NOT_NULL")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime thoiGianKetThuc;
    @DecimalMin(value = "1.00", message = "COUPONS_MIN_DISCOUNT_VALUE")
    BigDecimal giaTriGiam;

    BigDecimal giamToiDa;

    List<Long> listSanPhamChiTiet;
}

