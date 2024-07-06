//package com.dantn.weblaptop.nhanvien.Dto.Response;
//
//import com.dantn.weblaptop.constant.AccountStatus;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.validation.constraints.*;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@Builder
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@AllArgsConstructor
//@NoArgsConstructor
//public class NhanVien_Response {
//    private Integer id;
//
//    private String ma;
//
//    private String ten;
//
//    private String anh;
//
//    private Boolean gioiTinh;
//
//    private LocalDate ngaySinh;
//
//    private String diaChi;
//
//    private String sdt;
//
//    private String soCanCuocCongDan;
//
//    private String email;
//
//    private String matKhau;
//
//    private LocalDate ngayTao;
//
//    private LocalDate ngayCapNhat;
//
//    @Enumerated(EnumType.STRING)
//    private AccountStatus accountStatus;
//}
