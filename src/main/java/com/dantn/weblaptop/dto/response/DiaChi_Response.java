package com.dantn.weblaptop.dto.response;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

    private String idQuanHuyen;

    private String idPhuongXa;

    private String idTinhThanhPho;

    private String diaChiNhanHang;
}
