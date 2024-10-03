package com.dantn.weblaptop.dto.request.update_request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class UpdateHoaDonRequest {
    Long chuyenKhoan;
    Long tienMat;
    Integer loaiHoaDon;
    String ten;
    String sdt;
    @Email(message = "EMAIL_INVALID")
    String email;
    String diaChi;
    String tinh;
    String tenTinh;
    String huyen;
    String tenHuyen;
    String phuong;
    String tenPhuong;
    String ghiChu;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime ngayNhanHang;
}
