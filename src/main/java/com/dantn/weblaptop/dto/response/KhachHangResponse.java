package com.dantn.weblaptop.dto.response;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KhachHangResponse {
    Long id;

    String ma;

    Integer trangThai;

    String ten;

    String email;

    String sdt;

    LocalDateTime ngaySinh;

    Integer gioiTinh;

    String hinhAnh;

    Integer hangKhachHang;

    String sessionId;

    String status;

    BigDecimal tienGiamHang;
}
