package com.dantn.weblaptop.dto.request.update_request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateDiaChi {

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
    private Integer idQuanHuyen;

    @JsonProperty("idPhuongXa")
    @NotNull(message = "idPhuongXa cannot be null")
    private Integer idPhuongXa;

    @JsonProperty("idTinhThanhPho")
    @NotNull(message = "idTinhThanhPho cannot be null")
    private Integer idTinhThanhPho;

    @JsonProperty("diaChiNhanHang")
    @NotNull(message = "diaChiNhanHang cannot be null")
    @Size(min = 1, max = 255, message = "diaChiNhanHang must be between 1 and 255 characters")
    private String diaChiNhanHang;

    @JsonProperty("khach_hang_id")
    private Integer khach_hang_id;
}
