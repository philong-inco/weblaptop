package com.dantn.weblaptop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiaChi_Response {
    private Long id;

    private Integer trangThai;

    private Integer loaiDiaChi;

    private String tenNguoiNhan;

    private String sdtNguoiNhan;

    private String emailNguoiNhan;

    private String diaChiNhanHang;
}
