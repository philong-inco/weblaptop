package com.dantn.weblaptop.dotgiamgia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SanPhamChiTietDTO {
    private Long id;
    private Integer trangThai;
    private BigDecimal giaBan;
}
