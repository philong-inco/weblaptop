package com.dantn.weblaptop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrangThaiHoaDon_Dto {
    private Long soLuong;
    private Integer trangThaiHoaDon;
    private BigDecimal tiLeTrangThaiHoaDon;
}
