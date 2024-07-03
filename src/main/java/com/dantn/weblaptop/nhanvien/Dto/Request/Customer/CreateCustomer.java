package com.dantn.weblaptop.nhanvien.Dto.Request.Customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCustomer {
    @NotEmpty(message = "Name cannot is blank")
    @Pattern(regexp = "^[a-zA-Z\\p{L}\\s]+$", message = "Xảy ra lỗi với tên của khách hàng !.")
    String ho;
    @NotEmpty(message = "Số điện thoại không được để trống.")
    String sdt;
    @NotEmpty(message = "email không được để trống.")
    @Email(message = "Email chưa được nhập đúng cách.")
    String email;

    @NotEmpty(message = "password không được để trống.")
    String matKhau;

    @NotEmpty(message = "password comfirm không được để trống.")
    String confirmMatKhau;

    @NotEmpty(message = "Image không được để trống.")
    String hinhAnh;

    @NotNull(message = "Status không được để trống.")
    Integer trangThai;
}
