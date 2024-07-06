//package com.dantn.weblaptop.khachhang.Dto.Request;
//
//
//import jakarta.validation.constraints.*;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//
//import java.time.LocalDateTime;
//@Getter
//@Setter
//@Builder
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class CreateKhachHang_Request {
//    private Long id;
//
//    @NotBlank(message = "Mã nhân viên không được để trống")
//    @Size(max = 20, message = "Mã nhân viên không được vượt quá 20 ký tự")
//    private String ma;
//
//    @NotNull(message = "Trạng thái không được để trống")
//    @Min(value = 0, message = "Trạng thái phải là 0 hoặc 1")
//    @Max(value = 1, message = "Trạng thái phải là 0 hoặc 1")
//    private Integer trangThai;
//
//    @NotBlank(message = "Họ không được để trống")
//    @Size(max = 50, message = "Họ không được vượt quá 50 ký tự")
//    private String ho;
//
//    @NotBlank(message = "Tên không được để trống")
//    @Size(max = 50, message = "Tên không được vượt quá 50 ký tự")
//    private String ten;
//
//    @NotBlank(message = "Email không được để trống")
//    @Email(message = "Email không hợp lệ")
//    @Size(max = 100, message = "Email không được vượt quá 100 ký tự")
//    private String email;
//
//    @NotBlank(message = "Số điện thoại không được để trống")
//    @Pattern(regexp = "^(\\+84|0)\\d{9,10}$", message = "Số điện thoại không hợp lệ")
//    private String sdt;
//
//    @NotNull(message = "Ngày sinh không được để trống")
//    @Past(message = "Ngày sinh phải là một ngày trong quá khứ")
//    private LocalDateTime ngaySinh;
//
//    @NotNull(message = "Giới tính không được để trống")
//    @Min(value = 0, message = "Giới tính phải là 0 hoặc 1")
//    @Max(value = 1, message = "Giới tính phải là 0 hoặc 1")
//    private Integer gioiTinh;
//
//    @NotBlank(message = "Hình ảnh không được để trống")
//    @Size(max = 255, message = "Đường dẫn hình ảnh không được vượt quá 255 ký tự")
//    private String hinhAnh;
//
//    @NotBlank(message = "Session ID không được để trống")
//    @Size(max = 50, message = "Session ID không được vượt quá 50 ký tự")
//    private String sessionId;
//
//}
