package com.dantn.weblaptop.dto.request.update_request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateKhachHang {
    Long id;

    String ma;

    @NotNull(message = "Trạng thái không được để trống")
    Integer trangThai;

    @NotBlank(message = "Họ không được để trống")
    @Size(max = 50, message = "Họ không được vượt quá 50 ký tự")
    String ho;

    @NotBlank(message = "Tên không được để trống")
    @Size(max = 50, message = "Tên không được vượt quá 50 ký tự")
    String ten;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    String email;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Số điện thoại không hợp lệ")
    String sdt;

    @PastOrPresent(message = "Ngày sinh phải là quá khứ hoặc hiện tại")
    LocalDateTime ngaySinh;

    @NotNull(message = "Giới tính không được để trống")
    Integer gioiTinh;

    String hinhAnh;

    @NotNull(message = "Hạng khách hàng không được để trống")
    Integer hangKhachHang;
}
