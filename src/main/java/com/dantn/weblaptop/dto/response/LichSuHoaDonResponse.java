package com.dantn.weblaptop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LichSuHoaDonResponse extends BaseResponse {

    Integer trangThai;

    String ghiChuChoKhachHang;

    String ghiChuChoCuaHang;

    Long idKhachHanh;

    String maKhachHang;

    Long idNhanVien;

    String maNhanVien;

    Long hoaDon;

    String maHoaDon;


}
