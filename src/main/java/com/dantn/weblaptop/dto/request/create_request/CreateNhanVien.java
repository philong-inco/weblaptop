package com.dantn.weblaptop.dto.request.create_request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateNhanVien {
    Long id;

    String ma;
    @JsonProperty("dia_chi")
    String diaChi;

    Integer trang_thai;

    @JsonProperty("ten")
    @NotBlank(message = "Tên không được để trống")
    @Size(min = 1, max = 255, message = "Tên không được vượt quá 255 ký tự")
    String ten;

    @JsonProperty("email")
    @NotBlank(message = "Email không được để trống")
    @Size(min = 8, max = 255, message = "Email không vượt quá 255 ký tự")
    @Email(message = "Email sai định dạng")
    String email;

    String matKhau;

    @JsonProperty("sdt")
    @NotBlank(message = "SĐT không được để trống")
    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không hợp lệ. Phải bắt đầu bằng số 0 và có 10 chữ số.")
    String sdt;

    @JsonProperty("ngay_sinh")
    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    LocalDateTime ngaySinh;

    @JsonProperty("gioi_tinh")
    @NotNull(message = "Giới tính không được để trống")
    @Min(value = 0, message = "Giới tính phải là 0 hoặc 1")
    @Max(value = 1, message = "Giới tính phải là 0 hoặc 1")
    Integer gioiTinh;

    @JsonProperty("hinh_anh")
    String hinhAnh;

    LocalDateTime ngayBatDauLamViec;

    @JsonProperty("ngay_thoi_viet")
    LocalDateTime ngayThoiViec;

    Long idVaiTro;
    @JsonProperty("list_vai_tro")
    Set<String> listVaiTro;
}
