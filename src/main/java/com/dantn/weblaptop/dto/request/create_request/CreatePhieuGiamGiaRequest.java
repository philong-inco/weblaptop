package com.dantn.weblaptop.dto.request.create_request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePhieuGiamGiaRequest {

    String ma;
    @Size(min = 4, message = "COUPONS_NAME_MIN_4")
    String ten;
    String moTa;
    @NotNull(message = "COUPONS_START_DATE_NOT_NULL")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime ngayBatDau;
    @NotNull(message = "COUPONS_END_DATE_NOT_NULL")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime ngayHetHan;
    @NotNull(message = "COUPONS_DISCOUNT_TYPE_NOT_NULL")
    Integer loaiGiamGia;
    @NotNull(message = "COUPONS_DISCOUNT_VALUE_NOT_NULL")
    @Min(value = 1, message = "COUPONS_MIN_DISCOUNT_VALUE")
    BigDecimal giaTriGiamGia;
    @Min(value = 1, message = "COUPONS_MIN_ORDER_VALUE")
    BigDecimal giaTriDonToiThieu;
    BigDecimal giamToiDa;
    @NotNull(message = "COUPONS_APPLICABLE_SCOPE_NOT_NULL")
    Integer phamViApDung;
    @Min(value = 1, message = "COUPONS_MIN_QUANTITY")
    Integer soLuong;
    Long ngayTao;
    Long ngaySua;
    String nguoiTao;
    String nguoiSua;

    List<Long> listKhachHang;
}
