package com.dantn.weblaptop.dto.request.create_request;

import com.dantn.weblaptop.entity.nhanvien.VaiTro;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateNhanVien {
    Long id;

    String ma;

    @NotNull(message = "Trạng thái không được để trống")
    @Min(value = 0, message = "Trạng thái phải lớn hơn hoặc bằng 0")
    @Max(value = 1, message = "Trạng thái phải nhỏ hơn hoặc bằng 1")
    Integer trangThai;

    @NotBlank(message = "Số căn cước công dân không được để trống")
    @Pattern(regexp = "^\\d{12}$", message = "Số căn cước công dân không hợp lệ. Phải có 12 chữ số.")
    String cccd;

    @NotBlank(message = "Tên không được để trống")
    @Size(min = 1, max = 255, message = "Tên không được vượt quá 255 ký tự")
    String ten;

    @NotBlank(message = "Email không được để trống")
    @Size(min = 8, max = 255, message = "Email không vượt quá 255 ký tự")
    @Email(message = "Email sai định dạng")
    String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, max = 255, message = "Mật khẩu không vượt quá 255 ký tự")
    String matKhau;

    @NotBlank(message = "SĐT không được để trống")
    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không hợp lệ. Phải bắt đầu bằng số 0 và có 10 chữ số.")
    String sdt;

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    LocalDateTime ngaySinh;

    @NotNull(message = "Giới tính không được để trống")
    @Min(value = 0, message = "Giới tính phải là 0 hoặc 1")
    @Max(value = 1, message = "Giới tính phải là 0 hoặc 1")
    Integer gioiTinh;

    @NotBlank(message = "Hình ảnh không được để trống")
    @Size(max = 255, message = "Hình ảnh không được vượt quá 255 ký tự")
    String hinhAnh;

    @NotBlank(message = "Tài khoản ngân hàng không được để trống")
    @Size(max = 50, message = "Tài khoản ngân hàng không được vượt quá 50 ký tự")
    String taiKhoanNganHang;

    @NotNull(message = "Ngày bắt đầu làm việc không được để trống")
    @FutureOrPresent(message = "Ngày bắt đầu làm việc phải là ngày hiện tại hoặc tương lai")
    LocalDateTime ngayBatDauLamViec;

    LocalDateTime ngayThoiViec;

    List<VaiTro> vaiTroList;
}
