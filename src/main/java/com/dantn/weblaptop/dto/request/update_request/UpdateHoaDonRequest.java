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
//    Long idChuyenKhoan;
//    Long idTienMat;
//    BigDecimal soTienChuyenKhoan;
//    BigDecimal soTienMat;
    Integer thanhToanSau;
    Integer loaiHoaDon;
    String ten;
    String sdt;
    String email;
    String diaChi;
    String tinh;
    String tenTinh;
    String huyen;
    String tenHuyen;
    String phuong;
    String tenPhuong;
    String ghiChu;
    BigDecimal tienShip;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    LocalDateTime ngayNhanHangDuKien;
}
