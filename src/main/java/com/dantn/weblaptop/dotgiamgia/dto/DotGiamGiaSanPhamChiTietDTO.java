package com.dantn.weblaptop.dotgiamgia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DotGiamGiaSanPhamChiTietDTO {
    private Long id;
    private Integer trangThai;
    private SanPhamChiTietDTO sanPhamChiTiet;
    private DotGiamGiaDTO dotGiamGia;
}
