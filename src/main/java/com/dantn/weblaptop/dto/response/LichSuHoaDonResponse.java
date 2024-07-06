package com.dantn.weblaptop.dto.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LichSuHoaDonResponse {
    Long id;

    @JsonProperty("trang_thai")
    Integer trangThai;

    @JsonProperty( "ghi_chu_cho_khach_hang")
    String ghiChuChoKhachHang;

    @JsonProperty(  "ghi_chu_cho_cua_hang")
    String ghiChuChoCuaHang;

    @JsonProperty( "khach_hang_id")
    Long khachHangId;

    @JsonProperty(  "nhan_vien_id")
    Long nhanVienId;

    @JsonProperty(  "hoa_don_id")
    Long hoaDon;
}
