package com.dantn.weblaptop.nhanvien.Dto.Response;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeReponse {
    String ma;

    Integer trangThai;

    String cccd;

    String ten;

    String email;

    String matKhau;

    String sdt;

    LocalDateTime ngaySinh;

    Integer gioiTinh;

    String hinhAnh;

    String taiKhoanNganHang;

    LocalDateTime ngayBatDauLamViec;

    LocalDateTime ngayThoiViec;
}
