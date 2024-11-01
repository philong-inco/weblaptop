package com.dantn.weblaptop.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonSummaryDTO {
    private Long tongHoaDon;
    private Long tongSanPham;
    private LocalDate ngayThanhToan;
}
