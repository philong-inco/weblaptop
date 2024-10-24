package com.dantn.weblaptop.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TraCuDonHangResponse {
    HoaDonResponse hoaDon;
    List<LichSuHoaDonResponse> lichSuHoaDon;
    List<HoaDonHinhThucThanhToanResponse> lichSuThanhToan;
}
