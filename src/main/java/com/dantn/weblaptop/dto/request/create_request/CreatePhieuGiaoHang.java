package com.dantn.weblaptop.dto.request.create_request;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatePhieuGiaoHang {

//    Long id;
    String ma;
    Integer trangThai;
    String tenNguoiNhan;
    String sdtNguoiNhan;
    String diaChiNhanHang;
    String ghiChu;
    BigDecimal tienThuHo;
    Boolean choXemHang;
    Set<Long> serialNumberDaBans;
//    Long ngayTao;
//    Long ngaySua;
//    String nguoiTao;
//    String nguoiSua;
}
