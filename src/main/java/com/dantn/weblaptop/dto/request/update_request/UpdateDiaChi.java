package com.dantn.weblaptop.dto.request.update_request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateDiaChi {
    private Long id;

    private Integer trangThai;

    private Integer loaiDiaChi;

    @NotBlank(message = "Tên người nhận không được để trống")
    private String tenNguoiNhan;

    @Pattern(regexp = "\\d{10,11}", message = "Số điện thoại người nhận không hợp lệ")
    private String sdtNguoiNhan;

    @Email(message = "Email không hợp lệ")
    private String emailNguoiNhan;

    @NotNull(message = "idQuanHuyen cannot be null")
    @Positive(message = "idQuanHuyen must be a positive integer")
    private Integer idQuanHuyen;

    @NotNull(message = "idPhuongXa cannot be null")
    @Positive(message = "idPhuongXa must be a positive integer")
    private Integer idPhuongXa;

    @NotNull(message = "idTinhThanhPho cannot be null")
    @Positive(message = "idTinhThanhPho must be a positive integer")
    private Integer idTinhThanhPho;

    @NotNull(message = "diaChiNhanHang cannot be null")
    @Size(min = 1, max = 255, message = "diaChiNhanHang must be between 1 and 255 characters")
    private String diaChiNhanHang;}
