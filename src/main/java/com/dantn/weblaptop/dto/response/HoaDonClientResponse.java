package com.dantn.weblaptop.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class HoaDonClientResponse {
    HoaDonResponse hoaDon;
    List<SerialNumberDaBanResponse> serialNumber;
    List<LichSuHoaDonResponse> lichSuHoaDon;

}
