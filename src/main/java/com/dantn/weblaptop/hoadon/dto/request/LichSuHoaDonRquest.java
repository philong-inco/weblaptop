package com.dantn.weblaptop.hoadon.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LichSuHoaDonRquest {

    @JsonProperty("trang_thai")
    Integer trangThai;

    @JsonProperty("ghi_chu_cho_khach_hang")
    String ghiChuChoKhachHang;

    @JsonProperty("ghi_chu_cho_cua_hang")
    String ghiChuChoCuaHang;

    @JsonProperty("khach_hang_id")
    Long idKhachHang;

    @JsonProperty("nhan_vien_id")
    Long IdNhanVien;

    @JsonProperty("hoa_don_id")
    Long IdHoaDon;
}
