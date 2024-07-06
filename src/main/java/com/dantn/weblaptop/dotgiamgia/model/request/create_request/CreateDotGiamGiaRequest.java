package com.dantn.weblaptop.dotgiamgia.model.request.create_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateDotGiamGiaRequest {

    @NotBlank
    @Size(min = 1, max = 255, message = "Mã Không Được Trống Và Vượt Quá 255 ký tự !")
    @Pattern(regexp = "^(?!.*[+;\\-*\\/]).*$", message = "Mã không được chứa các ký tự đặc biệt như +, ;, -, *, Chia!")
    String ma;

    @NotBlank(message = "Tên Đợt Giảm Giá Trống !")
    @Size(min = 1, max = 255, message = "Tên Vượt Quá 255 ký tự !")
    @Pattern(regexp = "^(?!.*[+;\\-*\\/]).*$", message = "Tên không được chứa các ký tự đặc biệt như +, ;, -, *, Chia!")
    String ten;

    @NotBlank(message = "Trạng Thái Đợt Giảm Giá Trống !")
    @Pattern(regexp = "^[0-9]\\d*$", message = "Trạng Thái Không Được Trống")
    String trangThai;

    @NotBlank(message = "Mô Tả Đợt Giảm Giá Trống !")
    @Size(min = 1, max = 255, message = "Mô Tả Vượt Quá 255 ký tự !")
    @Pattern(regexp = "^(?!.*[+;\\-*\\/]).*$", message = "Mô Tả không được chứa các ký tự đặc biệt như +, ;, -, *, Chia!")
    String moTa;

    @NotBlank(message = "Loại Chiết Khấu không được để trống!")
    @Pattern(regexp = "^(?:100|[1-9]?[0-9])$", message = "Loại Chiết Khấu phải là số từ 1 đến 100!")
    String loaiChietKhau;

    LocalDateTime thoiGianBatDau;
    LocalDateTime thoiGianKetthuc;

    @NotBlank(message = "Giảm Tối Đa không được để trống!")
    @Pattern(regexp = "^[1-9]\\d*$", message = "Giảm Tối Đa phải là số dương!")
    String giamToiDa;
}