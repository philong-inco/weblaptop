package com.dantn.weblaptop.dto.request.create_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RevertLichSuHoaDonRequest {

    String ghiChuChoKhachHang;

    @NotBlank(message = "Ghi chú không được để trống")
    @Size(min = 50, message = "Ghi chú phải ít nhất 50 kí tự")
    String ghiChuChoCuaHang;

    Long idKhachHang;

    Long IdNhanVien;

    Long IdHoaDon;
}
