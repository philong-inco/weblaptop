//package com.dantn.weblaptop.nhanvien.Dto.Request;
//
//
//import com.dantn.weblaptop.constant.AccountStatus;
//import com.dantn.weblaptop.entity.nhanvien.VaiTro;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.validation.constraints.*;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Getter
//@Setter
//@Builder
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class CreateNhanVien_Request {
//    private Integer id;
//
//    private String ma;
//
//    @NotBlank(message = "Tên không để trống")
//    @Size(min = 5, max = 255, message = "Tên không vượt quá 255 ký tự")
//    private String ten;
//
//    private String anh;
//
//    @NotNull(message = "Giới tính không được để trống")
//    private Boolean gioiTinh;
//
//    @NotNull(message = "Ngày sinh không được để trống")
//    @Past(message = "Ngày sinh không được lớn hơn ngày hiện tại")
//    private LocalDate ngaySinh;
//
//    @NotBlank(message = "Địa chỉ không để trống")
//    @Size(min = 5, max = 255, message = "Địa chỉ không vượt quá 255 ký tự")
//    private String diaChi;
//
//        @NotBlank(message = "SĐT không được để trống")
//    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không hợp lệ. Phải bắt đầu bằng số 0 và có 10 chữ số.")
//    private String sdt;
//
//        @NotBlank(message = "Số căn cước công dân không để trống")
//    @Pattern(regexp = "^\\d{12}$", message = "Số căn cước công dân không hợp lệ. Phải có 12 chữ số.")
//    private String soCanCuocCongDan;
//
//        @NotBlank(message = "Email không được để trống")
//    @Size(min = 8, max = 255, message = "Email không vượt quá 255 ký tự")
//    @Email(message = "Email sai định dạng")
//    private String email;
//
//    @NotBlank(message = "Mật khẩu không để trống")
//    @Size(min = 8, max = 255, message = "Mật khẩu không vượt quá 255 ký tự")
//    private String matKhau;
//
//    private LocalDate ngayTao;
//
//    private LocalDate ngayCapNhat;
//
//    @Enumerated(EnumType.STRING)
//    private AccountStatus accountStatus;
//
//    List<VaiTro> idVaiTro;
//}
