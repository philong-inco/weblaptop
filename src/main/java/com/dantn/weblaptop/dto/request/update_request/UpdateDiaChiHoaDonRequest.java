package com.dantn.weblaptop.dto.request.update_request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateDiaChiHoaDonRequest {
    String diaChi;
    @NotBlank(message = "Chưa có tỉnh")
    String tinh;
    String tenTinh;
    @NotBlank(message = "Chưa có huyện")
    String huyen;
    String tenHuyen;
    @NotBlank(message = "Chưa có phường")
    String phuong;
    String tenPhuong;
    String ghiChu;
    @NotBlank(message = "NAME_NOT_BLANK")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "NAME_INVALID")
    String ten;
    @NotBlank(message = "PHONE_NOT_BLANK")
    @Pattern(regexp = "^0\\d{9}$", message = "PHONE_INVALID")
    String sdt;
    @NotBlank(message = "EMAIL_NOT_BLANK")
    @Email(message = "EMAIL_INVALID")
    String email;
    BigDecimal tienShip;
}
