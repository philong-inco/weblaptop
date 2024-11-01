package com.dantn.weblaptop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonDashboard_Dto {
    private Long tongHoaDon;
    private Long tongSanPham;
    private LocalDateTime ngayThanhToan;
}
