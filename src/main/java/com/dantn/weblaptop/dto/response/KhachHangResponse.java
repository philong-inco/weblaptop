package com.dantn.weblaptop.dto.response;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KhachHangResponse {
    Long id;

    String ma;

    Integer trangThai;


    String ho;


    String ten;


    String email;

    String sdt;

    LocalDateTime ngaySinh;

    Integer gioiTinh;

    String hinhAnh;

    Integer hangKhachHang;

    String sessionId;
}
