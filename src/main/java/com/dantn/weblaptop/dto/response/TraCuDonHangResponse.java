package com.dantn.weblaptop.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TraCuDonHangResponse {
    HoaDonResponse hoaDon;
    List<LichSuHoaDonResponse> lichSuHoaDon;
    List<HoaDonHinhThucThanhToanResponse> lichSuThanhToan;
    List<SerialNumberDaBanResponse> serialNumber;
}
