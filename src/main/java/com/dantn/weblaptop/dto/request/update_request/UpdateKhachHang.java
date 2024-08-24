package com.dantn.weblaptop.dto.request.update_request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("ten")
    @NotBlank(message = "Tên không được để trống")
    @Size(max = 50, message = "Tên không được vượt quá 50 ký tự")
    String ten;
    @JsonProperty("email")
    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    String email;
    @JsonProperty("sdt")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Số điện thoại không hợp lệ")
    String sdt;
    @JsonProperty("ngay_sinh")
    @PastOrPresent(message = "Ngày sinh phải là quá khứ hoặc hiện tại")
    LocalDateTime ngaySinh;
    @JsonProperty("gioi_tinh")
    @NotNull(message = "Giới tính không được để trống")
    Integer gioiTinh;
    @JsonProperty("hinhAnh")
    String hinhAnh;
}
