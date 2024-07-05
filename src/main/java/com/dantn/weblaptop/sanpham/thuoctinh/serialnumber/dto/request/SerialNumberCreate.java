package com.dantn.weblaptop.sanpham.thuoctinh.serialnumber.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SerialNumberCreate {
    Integer trangThai;
    String ma;
    LocalDateTime ngayNhap;
    Long sanPhamChiTietId;
}
