package com.dantn.weblaptop.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfomationKhachHang {
    String ten;
    String email;
    String sdt;
    LocalDateTime ngaySinh;
    Integer gioiTinh;


}
