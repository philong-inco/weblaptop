package com.dantn.weblaptop.nhanvien.Dto.Request.Employee;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEmployee {
    @NotBlank(message = "Password không được để trống.")
    @Size(min = 8, max = 16, message = "Password required 8-16 characters")
    String matKhau;
    @NotBlank(message = "Password không được để trống.")
    @Size(min = 8, max = 16, message = "Password required 8-16 characters")
    String confirmMatKhau;
    @NotBlank(message = "name không được để trống.")
    String ten;
    @NotBlank(message = "email không được để trống.")
    String email;
    @NotBlank(message = "NumberPhone không được để trống.")
    String sdt;
    @NotBlank(message = "date of birth không được để trống.")
    LocalDateTime ngaySinh;
    @NotBlank(message = "Image không được để trống.")
    String hinhAnh;
    @NotBlank(message = "NumberBanking không được để trống.")
    String taiKhoanNganHang;
    @NotNull(message = "Status không được để trống.")
    @Min(value = 0, message = "Status is invalid")
    @Max(value = 1, message = "Status is invalid")
    Integer trangThai;
    @NotNull(message = "Status không được để trống.")
    Integer idRole;
}
