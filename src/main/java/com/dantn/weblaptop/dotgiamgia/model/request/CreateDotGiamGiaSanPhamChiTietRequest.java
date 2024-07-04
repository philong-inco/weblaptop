package com.dantn.weblaptop.dotgiamgia.model.request;

import com.dantn.weblaptop.entity.dotgiamgia.DotGiamGia;
import com.dantn.weblaptop.entity.sanpham.SanPhamChiTiet;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateDotGiamGiaSanPhamChiTietRequest {
    Integer trangThai;
    Long sanPhamChiTiet;
    Long dotGiamGia;
}
