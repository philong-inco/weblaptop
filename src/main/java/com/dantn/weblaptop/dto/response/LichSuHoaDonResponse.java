<<<<<<< HEAD:src/main/java/com/dantn/weblaptop/hoadon/dto/response/LichSuHoaDonResponse.java
package com.dantn.weblaptop.hoadon.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
=======
package com.dantn.weblaptop.dto.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
>>>>>>> main:src/main/java/com/dantn/weblaptop/dto/response/LichSuHoaDonResponse.java
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LichSuHoaDonResponse {
    Long id;

    @JsonProperty("trang_thai")
    Integer trangThai;

    @JsonProperty("ghi_chu_cho_khach_hang")
    String ghiChuChoKhachHang;

    @JsonProperty("ghi_chu_cho_cua_hang")
    String ghiChuChoCuaHang;

    @JsonProperty("khach_hang_id")
    Long idKhachHanh;

    @JsonProperty("ma_khach_hang")
    String maKhachHang;

    @JsonProperty("nhan_vien_id")
    Long idNhanVien;

    @JsonProperty("ma_nhan_vien")
    String maNhanVien;

    @JsonProperty("hoa_don_id")
    Long hoaDon;

    @JsonProperty("ma_hoa_don")
    String maHoaDon;

    // ng tao
}
