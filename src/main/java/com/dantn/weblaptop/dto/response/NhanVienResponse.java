package com.dantn.weblaptop.dto.response;

import com.dantn.weblaptop.entity.nhanvien.VaiTro;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class NhanVienResponse {
    Long id;

    String ma;

    Integer trangThai;

    String cccd;

    String ten;

    String email;

    String matKhau;

    String sdt;
    String diaChi;

    LocalDateTime ngaySinh;

    Integer gioiTinh;

    String hinhAnh;

    String taiKhoanNganHang;

    LocalDateTime ngayBatDauLamViec;

    LocalDateTime ngayThoiViec;

    String vaiTro;

    Set<String> listVaiTro;

}
