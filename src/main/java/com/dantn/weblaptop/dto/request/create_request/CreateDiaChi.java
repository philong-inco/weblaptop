package com.dantn.weblaptop.dto.request.create_request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateDiaChi {
    private Long id;

    private Integer trangThai;

    private Integer loaiDiaChi;

    @JsonProperty("tenNguoiNhan")
    @NotBlank(message = "Tên người nhận không được để trống")
    private String tenNguoiNhan;

    @JsonProperty("sdtNguoiNhan")
    @Pattern(regexp = "\\d{10,11}", message = "Số điện thoại người nhận không hợp lệ")
    private String sdtNguoiNhan;

    @JsonProperty("emailNguoiNhan")
    @Email(message = "Email không hợp lệ")
    private String emailNguoiNhan;
    @JsonProperty("idQuanHuyen")
    @NotNull(message = "idQuanHuyen cannot be null")
    @Positive(message = "idQuanHuyen must be a positive integer")
    private Integer idQuanHuyen;

    @JsonProperty("idPhuongXa")
    @NotNull(message = "idPhuongXa cannot be null")
    @Positive(message = "idPhuongXa must be a positive integer")
    private Integer idPhuongXa;

    @JsonProperty("idTinhThanhPho")
    @NotNull(message = "idTinhThanhPho cannot be null")
    @Positive(message = "idTinhThanhPho must be a positive integer")
    private Integer idTinhThanhPho;

    @JsonProperty("diaChiNhanHang")
    @NotNull(message = "diaChiNhanHang cannot be null")
    @Size(min = 1, max = 255, message = "diaChiNhanHang must be between 1 and 255 characters")
    private String diaChiNhanHang;

    @JsonProperty("khach_hang_id")
    private Integer khach_hang_id;
}
