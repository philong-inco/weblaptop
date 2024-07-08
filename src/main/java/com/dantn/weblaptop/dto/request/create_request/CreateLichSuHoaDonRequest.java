package com.dantn.weblaptop.dto.request.create_request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLichSuHoaDonRequest {
    Integer trangThai;

    String ghiChuChoKhachHang;

    String ghiChuChoCuaHang;

    Long idKhachHang;

    Long IdNhanVien;

    Long IdHoaDon;
}
