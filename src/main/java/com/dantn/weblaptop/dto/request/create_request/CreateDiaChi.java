package com.dantn.weblaptop.dto.request.create_request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "Tên người nhận không được để trống")
    private String tenNguoiNhan;

    @Pattern(regexp = "\\d{10,11}", message = "Số điện thoại người nhận không hợp lệ")
    private String sdtNguoiNhan;

    @Email(message = "Email không hợp lệ")
    private String emailNguoiNhan;

    @NotBlank(message = "Địa chỉ nhận hàng không được để trống")
    private String diaChiNhanHang;
}
